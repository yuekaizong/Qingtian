package kaizone.songmaya.qingtian.controller;

import org.springframework.security.web.util.matcher.RequestMatcherEditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(){
        return "Secure Hello!";
    }
}
