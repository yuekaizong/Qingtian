package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import kaizone.songmaya.xuanguan.jpa.FooterJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @RequestMapping(value = "/editHome")
    public String editHome(Model model){
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "system/editHome";
    }

    @RequestMapping(value = "/editHome", method = RequestMethod.POST)
    @ResponseBody
    public String editHomeSave(@RequestBody List<DivEntity> model){
        System.out.println(model.toString());
        return "success";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Object getHomeData(Model model){
        return divJPA.findAll();
    }
}
