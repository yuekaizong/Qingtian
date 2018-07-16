package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.entity.DivCellEntity;
import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import kaizone.songmaya.xuanguan.jpa.FooterJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DivJPA divJPA;

    @Autowired
    private FooterJPA footerJPA;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/admin/editHome")
    public String editHome(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "admin/editHome";
    }

    @RequestMapping(value = "/admin/editHome", method = RequestMethod.POST)
    @ResponseBody
    public String editHomeSave(@RequestBody List<DivEntity> model) {
        logger.debug(model.toString());
        for (DivEntity item : model) {
            List<DivCellEntity> cells = item.getCells();
            if (cells != null) {
                int k = 1;
                for (DivCellEntity cell : cells) {
                    cell.setId(item.getId() * 100 + k);
                    k++;
                }
            }
            divJPA.save(item);
        }
        return "success";
    }

    @PostMapping("/admin/editHome1")
    public String editHomeSave1(@RequestParam Map<String, String> body) {
        System.out.println(body);
        return "successs";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Object getHomeData(Model model) {
        return divJPA.findAll();
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public Object saveHomeData(Model model) {
        System.out.println(model);
        return model.toString();
    }
}
