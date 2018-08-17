package kaizone.songmaya.smartns.config;

import kaizone.songmaya.smartns.jpa.UAuthUserTokenRepository;
import kaizone.songmaya.smartns.model.UAuthUserToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailsService implements ClientDetailsService {

    private Log logger = LogFactory.getLog(this.getClass());

    private String HC_RESOURCE_ID = "smartNS";
    private Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24;// 60 * 10

    @Autowired
    UAuthUserTokenRepository uAuthUserTokenRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // 根据传入的client_id查找用户信息
        logger.debug("loadClientByClientId: " + clientId);
        // 数据解密
        logger.debug("loadClientByClientId: " + clientId);
        UAuthUserToken userToken = uAuthUserTokenRepository.findByClientId(clientId);
        if (userToken == null) {
            logger.debug("未找到对应的Client信息: " + clientId);
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        logger.debug("ClientSecret: " + userToken.getClientSecret());

        BaseClientDetails clientDetails;
        String scopes = "read,write";// ,trust
        String grandTypes = "client_credentials,refresh_token";// ,authorization_code,implicit,password
        String authorities = "ROLE_CLIENT";// ,ROLE_USER
        clientDetails = new BaseClientDetails(clientId, HC_RESOURCE_ID, scopes, grandTypes, authorities);
        clientDetails.setClientSecret(userToken.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
        return clientDetails;
    }
}
