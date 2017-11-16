package kaizone.songmaya.qingtian.user.controller;

import kaizone.songmaya.qingtian.user.bean.UserBean;
import kaizone.songmaya.qingtian.user.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    /**
     * 查询所有用户列表
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public List<UserBean> list() {
        return userJPA.findAll();
    }
}
