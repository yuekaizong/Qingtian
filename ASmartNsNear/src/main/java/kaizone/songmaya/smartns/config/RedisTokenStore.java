package kaizone.songmaya.smartns.config;

import kaizone.songmaya.smartns.jpa.UAuthUserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * OAuth2服务实现redis存储token，支持集群能力 参考
 *
 * @see org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
 */
@Configurable(autowire = Autowire.BY_TYPE)
@Component
public class RedisTokenStore implements TokenStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTokenStore.class);


    @Autowired
    UAuthUserTokenRepository uAuthUserTokenRepository;


    private JedisConnectionFactory jedisConnectionFactory;
    private RedisTemplate<Object, Object> redisTemplate;


    private static final String userStore = "__token_app_userStore";
    private final String accessTokenStore = "__token_app_accessTokenStore";
    private final String authenticationToAccessTokenStore = "__token_app_authenticationToAccessTokenStore";
    private final String userNameToAccessTokenStore = "__token_app_userNameToAccessTokenStore";
    private final String clientIdToAccessTokenStore = "__token_app_clientIdToAccessTokenStore";
    private final String refreshTokenStore = "__token_app_refreshTokenStore";
    private final String accessTokenToRefreshTokenStore = "__token_app_accessTokenToRefreshTokenStore";
    private final String authenticationStore = "__token_app_authenticationStore";
    private final String refreshTokenAuthenticationStore = "__token_app_refreshTokenAuthenticationStore";
    private final String refreshTokenToAccessTokenStore = "__token_app_refreshTokenToAccessTokenStore";
    private final String expiryQueue = "__token_app_expiryQueue";

    private static final int DEFAULT_FLUSH_INTERVAL = 1000;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private int flushInterval = DEFAULT_FLUSH_INTERVAL;
    private AtomicInteger flushCounter = new AtomicInteger(0);

    public RedisTokenStore(JedisConnectionFactory jedisConnectionFactor) {
        redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactor);

        redisTemplate.setStringSerializer(new RedisSerializer<String>() {
            @Override
            public byte[] serialize(String s) throws SerializationException {
                return RedisTokenStore.serialize(s);
            }

            @Override
            public String deserialize(byte[] bytes) throws SerializationException {
                return (String) RedisTokenStore.unserialize(bytes);
            }
        });

        RedisSerializer<Object> defaultObjectSerializer = new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return RedisTokenStore.serialize(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return RedisTokenStore.unserialize(bytes);
            }
        };

        redisTemplate.setDefaultSerializer(defaultObjectSerializer);

        redisTemplate.setKeySerializer(defaultObjectSerializer);
        redisTemplate.setValueSerializer(defaultObjectSerializer);
        redisTemplate.setHashKeySerializer(defaultObjectSerializer);
        redisTemplate.setHashValueSerializer(defaultObjectSerializer);

        redisTemplate.afterPropertiesSet();
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken oAuth2AccessToken) {
        __debug("=====readAuthentication: %s", oAuth2AccessToken.getValue());
        return readAuthentication(oAuth2AccessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        __debug("=====readAuthentication: %s", token);
        return (OAuth2Authentication) redisTemplate.opsForHash().get(authenticationStore, token);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        __debug("=====storeAccessToken: %s", oAuth2AccessToken.getValue());
        if (this.flushCounter.incrementAndGet() >= this.flushInterval) {
            flush();
            this.flushCounter.set(0);
        }

        String key = oAuth2AccessToken.getValue();

        String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
        hset(userStore, key, getUserIdByClientId(clientId));

        hset(accessTokenStore, key, oAuth2AccessToken);
        hset(authenticationStore, key, oAuth2Authentication);
        hset(authenticationToAccessTokenStore, authenticationKeyGenerator.extractKey(oAuth2Authentication),
                oAuth2AccessToken);
        if (!oAuth2Authentication.isClientOnly()) {
            addToCollection(userNameToAccessTokenStore, getApprovalKey(oAuth2Authentication),
                    oAuth2AccessToken);
        }
        addToCollection(clientIdToAccessTokenStore, clientId, oAuth2AccessToken);

        if (oAuth2AccessToken.getExpiration() != null) {
            TokenExpiry expiry = new TokenExpiry(oAuth2AccessToken.getValue(), oAuth2AccessToken.getExpiration());
            putExpiryQueue(expiry);
        }
        if (oAuth2AccessToken.getRefreshToken() != null && oAuth2AccessToken.getRefreshToken().getValue() != null) {
            hset(refreshTokenToAccessTokenStore, oAuth2AccessToken.getRefreshToken().getValue(), key);
            hset(accessTokenToRefreshTokenStore, key, oAuth2AccessToken.getRefreshToken().getValue());
        }

    }


    /**
     * 通过access_token获取用户ID
     *
     * @param token
     * @return
     */
    public String getUserIdByToken(String token) {
        return (String) hget(userStore, token);
    }

    private String getUserIdByClientId(String clientId) {
        try {
            return uAuthUserTokenRepository.findByClientId(clientId).getUserId();
        } catch (Exception e) {
            LOGGER.error("通过ClientID查询用户ID失败：" + clientId + " ==> " + e.getMessage());
            return "";
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        __debug("=====readAccessToken: %s", tokenValue);
        return (OAuth2AccessToken) hget(accessTokenStore, tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        __debug("=====removeAccessToken: %s", oAuth2AccessToken.getValue());
        removeAccessToken(oAuth2AccessToken.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        __debug("=====removeAccessToken: %s", tokenValue);
        String key = tokenValue;
        OAuth2AccessToken removed = (OAuth2AccessToken) hget(accessTokenStore, key);
        hmdel(accessTokenStore, key);
        hmdel(accessTokenToRefreshTokenStore, key);

        OAuth2Authentication authentication = (OAuth2Authentication) hget(authenticationStore, key);
        hmdel(authenticationStore, key);
        if (authentication != null) {
            hmdel(authenticationToAccessTokenStore, authenticationKeyGenerator.extractKey(authentication));
            try {
                String authKey = authentication.getName();
                Collection<OAuth2AccessToken> tokens = (Collection<OAuth2AccessToken>) hget(userNameToAccessTokenStore, authKey);
                if (tokens != null) {
                    tokens.remove(removed);
                    hset(userNameToAccessTokenStore, authKey, tokens);
                }

                String clientId = authentication.getOAuth2Request().getClientId();
                Collection<OAuth2AccessToken> clientTokens = (Collection<OAuth2AccessToken>) hget(clientIdToAccessTokenStore, clientId);
                if (clientTokens != null) {
                    clientTokens.remove(removed);
                    hset(clientIdToAccessTokenStore, clientId, clientTokens);
                }

            } catch (Exception e) {
                LOGGER.error("RedisTokenStore.removeAccessToken - " + e.getMessage());
            }
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication oAuth2Authentication) {
        __debug("=====storeAccessToken: %s", oAuth2RefreshToken.getValue());
        String key = oAuth2RefreshToken.getValue();
        hset(refreshTokenStore, key, oAuth2RefreshToken);
        hset(refreshTokenAuthenticationStore, key, oAuth2Authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        __debug("=====readRefreshToken: %s", tokenValue);
        return (OAuth2RefreshToken) hget(refreshTokenStore, tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        __debug("=====readAuthenticationForRefreshToken: %s", oAuth2RefreshToken.getValue());
        return (OAuth2Authentication) hget(refreshTokenAuthenticationStore, oAuth2RefreshToken.getValue());
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        __debug("=====removeRefreshToken: %s", oAuth2RefreshToken.getValue());
        String key = oAuth2RefreshToken.getValue();
        hmdel(refreshTokenStore, key);
        hmdel(refreshTokenAuthenticationStore, key);
        hmdel(refreshTokenToAccessTokenStore, key);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        __debug("=====removeAccessTokenUsingRefreshToken: %s", oAuth2RefreshToken.getValue());
        String key = oAuth2RefreshToken.getValue();
        String accessToken = (String) hget(refreshTokenToAccessTokenStore, key);
        hmdel(refreshTokenToAccessTokenStore, key);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        __debug("=====getAccessToken: %s", oAuth2Authentication.toString());
        String key = authenticationKeyGenerator.extractKey(oAuth2Authentication);
        OAuth2AccessToken accessToken = (OAuth2AccessToken) hget(authenticationToAccessTokenStore, key);
        if (accessToken != null) {
            OAuth2Authentication existedOAuth2Auth = readAuthentication(accessToken.getValue());
            if (existedOAuth2Auth == null) {
                __debug("ExistedOAuth2Auth is null.");
                storeAccessToken(accessToken, oAuth2Authentication);
            } else if (!key.equals(authenticationKeyGenerator.extractKey(existedOAuth2Auth))) {
                __debug("Key of ExistedOAuth2Auth is not matching.");
                storeAccessToken(accessToken, oAuth2Authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        __debug("=====findTokensByClientIdAndUserName: %s - %s", clientId, userName);
        Collection<OAuth2AccessToken> result = (Collection<OAuth2AccessToken>) hget(userNameToAccessTokenStore,
                getApprovalKey(clientId, userName));
        return result != null ? Collections.<OAuth2AccessToken>unmodifiableCollection(result)
                : Collections.<OAuth2AccessToken>emptySet();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        __debug("=====findTokensByClientId: %s", clientId);
        Collection<OAuth2AccessToken> result = (Collection<OAuth2AccessToken>) hget(clientIdToAccessTokenStore,
                clientId);
        return result != null ? Collections.<OAuth2AccessToken>unmodifiableCollection(result)
                : Collections.<OAuth2AccessToken>emptySet();
    }

    private void addToCollection(String collectionName, String key, OAuth2AccessToken token) {
        try {
            Collection<OAuth2AccessToken> collection = (Collection<OAuth2AccessToken>) hget(collectionName, key);
            if (collection == null) {
                collection = new HashSet<>();
            }
            collection.add(token);
            redisTemplate.opsForHash().put(collectionName, key, collection);
        } catch (Exception e) {
            LOGGER.error("RedisTokenStore.addToCollection - " + e.getMessage());
        }
    }

    private void putExpiryQueue(TokenExpiry expiry) {
        try {
            long start = System.nanoTime();
            String key = expiryQueue;
            RedisDelayQueue<TokenExpiry> delayQueue = (RedisDelayQueue<TokenExpiry>) redisTemplate.opsForValue().get(key);
            if (delayQueue == null) {
                delayQueue = new RedisDelayQueue<>();
            }
            delayQueue.remove(expiry);
            delayQueue.put(expiry);

            redisTemplate.opsForValue().set(key, delayQueue);
        } catch (Exception e) {
            LOGGER.error("RedisTokenStore.putExpiryQueue - " + e.getMessage());
        }
    }

    private String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? ""
                : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    private void flush() {

        try {

            String key = expiryQueue;
            RedisDelayQueue<TokenExpiry> delayQueue = (RedisDelayQueue<TokenExpiry>)
                    redisTemplate.opsForValue().get(key);
            if (delayQueue == null) {
                delayQueue = new RedisDelayQueue<>();
            }
            TokenExpiry expiry = (TokenExpiry) delayQueue.poll();
            while (expiry != null) {
                removeAccessToken(expiry.getValue());
                expiry = (TokenExpiry) delayQueue.poll();
            }
            redisTemplate.opsForValue().set(key, delayQueue);

        } catch (Exception e) {
            LOGGER.error("RedisTokenStore.flush - " + e.getMessage());
        }
    }

    private void __debug(String msg, Object... args) {
        LOGGER.debug(String.format(msg, args));
    }

    private static class RedisDelayQueue<E extends Delayed> extends DelayQueue implements Serializable {

    }

    private static class TokenExpiry implements Delayed {

        private final long expiry;

        private final String value;

        public TokenExpiry(String value, Date date) {
            this.value = value;
            this.expiry = date.getTime();
        }

        public int compareTo(Delayed other) {
            if (this == other) {
                return 0;
            }
            long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
            return (diff == 0l ? 0 : ((diff < 0) ? -1 : 1));
        }

        public long getDelay(TimeUnit unit) {
            return expiry - System.currentTimeMillis();
        }

        public String getValue() {
            return value;
        }

    }


    public void cleanAll() {
        List<String> list = new ArrayList<>();
        list.add(userStore);
        list.add(accessTokenStore);
        list.add(authenticationToAccessTokenStore);
        list.add(userNameToAccessTokenStore);
        list.add(clientIdToAccessTokenStore);
        list.add(refreshTokenStore);
        list.add(accessTokenToRefreshTokenStore);
        list.add(authenticationStore);
        list.add(refreshTokenAuthenticationStore);
        list.add(refreshTokenToAccessTokenStore);

        list.forEach(key -> {
            final byte[] rawKey = redisTemplate.getStringSerializer().serialize(key);
            Long delReault = redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) {
                    return connection.del(rawKey);
                }
            }, true);
            LOGGER.debug("delete " + key + " return " + delReault);
        });
    }


    public Object hget(String key, String hashKey) {
        try {
            validateValue(key, hashKey);
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            LOGGER.error("hget", e);
        }

        return null;
    }

    public boolean hset(String key, String hashKey, Object hashVal) {
        try {
            validateValue(key, hashKey, hashVal);
            redisTemplate.opsForHash().put(key, hashKey, hashVal);
            return true;
        } catch (Exception e) {
            LOGGER.error("hset", e);
        }
        return false;
    }

    public void hmdel(String key, String hashKey) {
        try {
            validateValue(key, hashKey);
            redisTemplate.opsForHash().delete(key, hashKey);
        } catch (Exception e) {
            LOGGER.error("hmdel", e);
        }
    }


    public static void validateValue(Object... value) throws Exception {
        if (value == null) {
            throw new Exception("Redis无法处理值为null的数据");
        } else {
            Object[] var1 = value;
            int var2 = value.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Object obj = var1[var3];
                if (obj == null) {
                    throw new Exception("Redis无法处理值为null的数据");
                }

                if (obj instanceof Map) {
                    Iterator iterator = ((Map) obj).entrySet().iterator();

                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> e = (Map.Entry) iterator.next();
                        if (e.getValue() == null) {
                            throw new Exception("Redis无法处理值为null的数据");
                        }
                    }
                }
            }

        }
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (IOException var3) {
                return null;
            } catch (ClassNotFoundException var4) {
                return null;
            }
        }
    }
}
