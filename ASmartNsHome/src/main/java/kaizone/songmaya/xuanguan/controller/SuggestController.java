package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.entity.SuggestEntity;
import kaizone.songmaya.xuanguan.jpa.SuggestJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class SuggestController {

    @Autowired
    private SuggestJPA suggestJpa;

    @PostMapping("/suggest/save")
    @ResponseBody
    public String save(@RequestParam Map<String, String> body) {
        SuggestEntity entity = new SuggestEntity();
        entity.setNickname(body.get("nickname"));
        entity.setEmail(body.get("email"));
        entity.setContent(body.get("content"));
        suggestJpa.save(entity);
        return "你的建议已成功提交，谢谢";
    }

    @PostMapping("/suggest/save1")
    @ResponseBody
    public String save(@RequestBody SuggestEntity body) {
        System.out.println(body);
        return "success";
    }
}
