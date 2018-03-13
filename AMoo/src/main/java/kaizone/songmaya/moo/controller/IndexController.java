package kaizone.songmaya.moo.controller;

import kaizone.songmaya.moo.base.entity.UserEntity;
import kaizone.songmaya.moo.base.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {


    @RequestMapping(path = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/demo")
    public String demo(Map<String, Object> map) {
        map.put("descrip", "It's a springboot integrate freemarker's demo!!!!");
        return "demo";
    }
}
