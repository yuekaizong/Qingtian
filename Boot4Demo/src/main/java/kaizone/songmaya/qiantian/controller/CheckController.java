package kaizone.songmaya.qiantian.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {

    @RequestMapping(value = "/exception/{number}")
    public String exception(@PathVariable int number) {
        System.out.println(20 / number);
        return "get index page successfully.";
    }
}
