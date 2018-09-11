package kaizone.songmaya.smartns.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public Map<String, Object> success(Object body) {
        Map<String, Object> result = new HashMap<>();
        result.put("head", head("00000", "success"));
        result.put("body", body);
        return result;
    }

    public Map<String, Object> fail(Object fail) {
        Map<String, Object> result = new HashMap<>();
        result.put("head", head("A01234", fail.toString()));
        return result;
    }

    public Map<String, String> head(String flag, String msg) {
        Map<String, String> result = new HashMap<>();
        result.put("flag", flag);
        result.put("msg", msg);
        return result;
    }


}
