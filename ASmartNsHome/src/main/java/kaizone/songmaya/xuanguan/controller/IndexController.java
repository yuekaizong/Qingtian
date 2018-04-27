package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import kaizone.songmaya.xuanguan.jpa.FooterJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

//    @RequestMapping(value = "/editHome", method = RequestMethod.POST)
//    @ResponseBody
//    public String editHomeSave(@RequestBody List<DivEntity> model){
//        System.out.println(model.toString());
//        return "success";
//    }

    @PostMapping("/editHome")
    public String editHomeSave1(@RequestParam Map<String, String> body){
        System.out.println(body);
        return "successs";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Object getHomeData(Model model){
        return divJPA.findAll();
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public Object saveHomeData(Model model){
        System.out.println(model);
        return model.toString();
    }
}
