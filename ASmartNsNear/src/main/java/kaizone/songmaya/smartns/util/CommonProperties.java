package kaizone.songmaya.smartns.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(
        prefix = "common"
)
@Component
public class CommonProperties {
    private static Map<String, Object> sign;
    private static Map<String, Object> redis;
    private static Map<String, Object> address;
    private static Map<String, Object> path;
    private static Map<String, Object> file;
    private static Map<String, Object> other;
    private static Map<String, Object> sms;
    private static Map<String, Object> app;

    public CommonProperties() {
    }

    public Map<String, Object> getSign() {
        return sign;
    }

    public void setSign(Map<String, Object> sign) {
        sign = sign;
    }

    public Map<String, Object> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, Object> redis) {
        redis = redis;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        address = address;
    }

    public Map<String, Object> getPath() {
        return path;
    }

    public void setPath(Map<String, Object> path) {
        path = path;
    }

    public Map<String, Object> getFile() {
        return file;
    }

    public void setFile(Map<String, Object> file) {
        file = file;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        other = other;
    }

    public Map<String, Object> getSms() {
        return sms;
    }

    public void setSms(Map<String, Object> sms) {
        sms = sms;
    }

    public Map<String, Object> getApp() {
        return app;
    }

    public void setApp(Map<String, Object> app) {
        app = app;
    }

    public static Object get(String key) {
        if (key != null && !key.equals("")) {
            if (key.contains(".")) {
                String[] keys = key.split("\\.");
                if (keys.length == 2) {
                    Map<String, Object> map = (Map)get(keys[0]);
                    if (map != null) {
                        return map.get(keys[1]);
                    }
                }
            } else {
                if (key.equals("sign")) {
                    return sign;
                }

                if (key.equals("redis")) {
                    return redis;
                }

                if (key.equals("address")) {
                    return address;
                }

                if (key.equals("path")) {
                    return path;
                }

                if (key.equals("file")) {
                    return file;
                }

                if (key.equals("other")) {
                    return other;
                }

                if (key.equals("sms")) {
                    return sms;
                }

                if (key.equals("app")) {
                    return app;
                }
            }

            return null;
        } else {
            return null;
        }
    }
}
