package kaizone.songmaya.qiantian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cors")
public class CorsController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "this is cors info";
    }
}
