package kaizone.songmaya.smartns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2ServerConfiguration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        private String HC_RESOURCE_ID = "smartNs";

        @Value("${common.app.checkAuth}")
        private Boolean checkAuth;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(HC_RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            if (checkAuth ==null || !checkAuth){
                http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
            }else{
                http.authorizeRequests().antMatchers("/",
                        "/redisKey",
                        "/redisValue",
                        "/loan/list",
                        "/loan/dict/userInfoParams",
                        "/loan/apply/setup1",
                        "/loan/apply/setup2",
                        "/customer/save",
                        "/customer/login")
                        .permitAll().anyRequest().authenticated().and().headers().frameOptions().sameOrigin();
            }
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private MyClientDetailsService clientDetailsService;

        @Autowired
        JedisConnectionFactory jedisConnectionFactor;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
//                    .tokenStore(tokenStore())
                    .reuseRefreshTokens(true) //刷新token不失效
                    .authenticationManager(this.authenticationManager)
                    .pathMapping("/oauth/token", "/app/oauth/token")
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService);
        }

        @Bean
        public TokenStore tokenStore() {
            return new RedisTokenStore(jedisConnectionFactor);
        }
    }


}
