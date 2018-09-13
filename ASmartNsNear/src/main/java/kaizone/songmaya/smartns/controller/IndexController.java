package kaizone.songmaya.smartns.controller;

import kaizone.songmaya.smartns.jpa.CustomerJpa;
import kaizone.songmaya.smartns.jpa.UAuthUserTokenRepository;
import kaizone.songmaya.smartns.model.Customer;
import kaizone.songmaya.smartns.model.UAuthUserToken;
import kaizone.songmaya.smartns.service.token.SelfTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController extends BaseController {

    @Autowired
    UAuthUserTokenRepository uAuthUserTokenRepository;

    @Autowired
    private SelfTokenService selfTokenService;

    @Autowired
    private CustomerJpa customerJpa;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value = "/")
    public String home() {
        return "Hello Near!";
    }

    @RequestMapping(value = "/customer/login", method = RequestMethod.PUT)
    public Map<String, Object> customerLogin(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap();

        String userId = (String) params.get("ssoId");
        String deviceId = (String) params.get("deviceId");
        String deviceType = (String) params.get("deviceType");
        String password = (String) params.get("password");

        if (StringUtils.isEmpty(deviceId)) {
            return fail("未知设备号");
        }

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)) {
            return fail("账号密码不能为空");
        }

        Customer customer = customerJpa.findBySsoId(userId);
        if (customer == null) {
            return fail("不存在客户");
        }
        if (!StringUtils.equals(customer.getPassword(), password)) {
            return fail("密码错误");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String clientSecret = passwordEncoder.encode(deviceId + userId);
        storeToken(userId, deviceId, clientSecret, deviceType);
        OAuth2AccessToken token = selfTokenService.createAccessToken(deviceId);
//        result.put("login", "Y");
        result.put("token", token);
        return success(result);
    }

    @RequestMapping(value = "/customer/save", method = RequestMethod.POST)
    public Map<String, Object> customerSave(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("ssoId");
        String username = (String) params.get("username");
        String nickname = (String) params.get("nickname");
        String deviceId = (String) params.get("deviceId");
        String password = (String) params.get("password");
        String address = (String) params.get("address");

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)) {
            return fail("账号密码不能为空");
        }

        Customer c = customerJpa.findBySsoId(userId);
        if (c == null) {
            c = new Customer();
        }

        c.setAddress(address);
        c.setPassword(password);
        c.setDeviceId(deviceId);
        c.setUsername(username);
        c.setNickname(nickname);
        c.setSsoId(userId);

        customerJpa.save(c);

        Map<String, Object> result = new HashMap();
        result.put("save", "Y");
        return success(result);
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

    @RequestMapping(value = "/testRedis/set")
    public Map<String, Object> redisSet(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return success("redis set success");
    }

    @RequestMapping(value = "/testRedis/get")
    public Map<String, Object> redisSet(String key) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        return success(value);
    }

    @RequestMapping(value = "/ac")
    public Map<String, Object> ac() {
        return success("这是一个需要认证的页面");
    }

}
