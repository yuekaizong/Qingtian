package kaizone.songmaya.qingtian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(){
        return "Hello User!";
    }
}
