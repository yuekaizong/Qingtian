package kaizone.songmaya.smartns.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.HashSet;
import java.util.Set;

public class SelfTokenServiceImpl implements SelfTokenService {

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public OAuth2AccessToken createAccessToken(String clientId) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("role_client");
        Set<String> scope = new HashSet<>();
        scope.add("read");
        scope.add("write");
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        OAuth2Request request = new OAuth2Request(null, clientId, authorities, true, scope, null, null, null, null);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request, null);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        return oAuth2AccessToken;
    }
}
