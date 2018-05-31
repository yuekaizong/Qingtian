package kaizone.songmaya.qingtian.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import kaizone.songmaya.qingtian.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "hi")
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @RequestMapping(value = "go")
    public String go(@RequestParam String name){
        return helloService.goService(name);
    }

    @RequestMapping(value = "yaya")
    public String yaya(){
        return helloService.yaya();
    }

    @RequestMapping(value = "youxi")
    public String youxi(){
        return "youxi";
    }

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping(value = "yala")
    public String yala(){
        return helloService.yala();
    }


}
