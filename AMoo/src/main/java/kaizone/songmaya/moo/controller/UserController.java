package kaizone.songmaya.moo.controller;

import kaizone.songmaya.moo.base.entity.UserEntity;
import kaizone.songmaya.moo.base.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    @RequestMapping(path = "/list")
    @ResponseBody
    public List<UserEntity> userList() {
        return userJPA.findAll();
    }
}
