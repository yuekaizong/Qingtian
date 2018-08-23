package kaizone.songmaya.smartns.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public Map<String, Object> success(Object body) {
        Map<String, Object> result = new HashMap<>();
        result.put("head", null);
        result.put("body", body);
        return result;
    }

    public Map<String, Object> fail(Object fail) {
        Map<String, Object> result = new HashMap<>();
        result.put("head", null);
        result.put("msg", fail);
        return result;
    }


}
