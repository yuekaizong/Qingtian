package kaizone.songmaya.smartns.service.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public interface SelfTokenService {
    OAuth2AccessToken createAccessToken(String clientId);
}
