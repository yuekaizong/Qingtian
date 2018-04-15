package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.jpa.DivJPA;
import kaizone.songmaya.xuanguan.jpa.FooterJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private DivJPA divJPA;

    @Autowired
    private FooterJPA footerJPA;

    @RequestMapping(value = "/")
    public String home(Model model){

        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "index";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Object getHomeData(Model model){
        return divJPA.findAll();
    }
}
