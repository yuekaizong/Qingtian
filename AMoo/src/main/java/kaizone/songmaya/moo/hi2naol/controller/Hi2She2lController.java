package kaizone.songmaya.moo.hi2naol.controller;

import kaizone.songmaya.moo.base.ApiResult;
import kaizone.songmaya.moo.base.ApiResultGenerator;
import kaizone.songmaya.moo.base.util.SecurityUtils;
import kaizone.songmaya.moo.hi2naol.entity.Hi2She2lEntity;
import kaizone.songmaya.moo.hi2naol.jpa.Hi2she2lJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.PortUnreachableException;
import java.security.KeyException;

@RequestMapping("/shell")
@Controller
public class Hi2She2lController {

    @Autowired
    private Hi2she2lJPA hi2she2lJPA;

    @RequestMapping(path = "/list")
    @ResponseBody
    public ApiResult list(@RequestParam("key") String key) {
        if (key == null || key.length() == 0) {
            return ApiResultGenerator.errorResult("key不能为空", new KeyException());
        }
        if (!"123456".equals(key)) {
            return ApiResultGenerator.errorResult("key不对称", new KeyException());
        }
        return ApiResultGenerator.successResult(hi2she2lJPA.findAll());
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "hi2she2l_edit";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult save(Hi2She2lEntity entity) {
        try {
            entity.setName(SecurityUtils.encryptDES(entity.getName()));
            entity.setFade(SecurityUtils.encryptDES(entity.getFade()));
            hi2she2lJPA.save(entity);
            return ApiResultGenerator.successResult(entity);
        } catch (Exception e) {
            return ApiResultGenerator.errorResult("", e);
        }
    }

    @RequestMapping(path = "/key", method = RequestMethod.GET)
    @ResponseBody
    public String key() {
        return "123456";
    }
}
