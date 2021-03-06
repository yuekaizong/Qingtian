package kaizone.songmaya.qingtian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @Autowired
    private Config config;

    @Autowired
    private MockResponseConfig mock;

    @RequestMapping(value = "app/config")
    public Map<String, Object> config() {
        Map<String, Object> data = new HashMap<>();
        data.put("samples", config.getSamples());
        return data;
    }

    @RequestMapping(value = "app/mock")
    public Map<String, Object> mock() {
        Map<String, Object> data = new HashMap<>();
        data.put("mock", mock.getMockItems());
        return data;
    }
}
