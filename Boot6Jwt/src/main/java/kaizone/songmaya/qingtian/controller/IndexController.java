package kaizone.songmaya.qingtian.controller;

import javafx.scene.chart.ValueAxis;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index(){
        return "success";
    }
}
