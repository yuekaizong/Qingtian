package kaizone.songmaya.smartns.controller;

import kaizone.songmaya.smartns.jpa.UAuthUserTokenRepository;
import kaizone.songmaya.smartns.model.UAuthUserToken;
import kaizone.songmaya.smartns.service.token.SelfTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    UAuthUserTokenRepository uAuthUserTokenRepository;

    @Autowired
    private SelfTokenService selfTokenService;


    @RequestMapping(value = "/")
    public String home() {
        return "Hello Near!";
    }

    @RequestMapping(value = "/customerLogin", method = RequestMethod.PUT)
    public Map<String, Object> customerLogin(@RequestBody Map<String, Object> userMap) {
        Map<String, Object> result = new HashMap();

        String userId = (String) userMap.get("userId");
        String deviceId = (String) userMap.get("deviceId");
        String deviceType = (String) userMap.get("deviceType");
        String password = (String) userMap.get("password");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String clientSecret = passwordEncoder.encode(deviceId + userId);
        storeToken(userId, deviceId, clientSecret, deviceType);
        selfTokenService.createAccessToken(deviceId);
        result.put("login", "success");
        return result;
    }

    public void storeToken(String userId, String deviceId, String clientSecret, String deviceType) {
        UAuthUserToken userToken = new UAuthUserToken();
        userToken.setUserId(userId);
        userToken.setClientId(deviceId);
        userToken.setClientSecret(clientSecret);
        userToken.setRegisterDate(new Date());
        userToken.setDeviceType(deviceType);
        uAuthUserTokenRepository.save(userToken);
    }
}
