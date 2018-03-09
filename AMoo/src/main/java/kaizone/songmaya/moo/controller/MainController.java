package kaizone.songmaya.moo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "print", method = RequestMethod.GET)
    public String print(){
        return "print main";
    }

}
