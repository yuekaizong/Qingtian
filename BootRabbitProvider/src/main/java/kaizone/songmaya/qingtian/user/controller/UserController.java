package kaizone.songmaya.qingtian.user.controller;

import kaizone.songmaya.qingtian.user.entity.UserEntity;
import kaizone.songmaya.qingtian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save")
    public UserEntity save(UserEntity userEntity) throws Exception
    {
        userService.save(userEntity);
        return userEntity;
    }
}
