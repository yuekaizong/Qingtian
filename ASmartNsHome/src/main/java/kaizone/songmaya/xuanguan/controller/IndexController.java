package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.jpa.DivJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private DivJPA divJPA;

    @RequestMapping(value = "/")
    public String home(Model model){

        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        return "index";
    }
}
