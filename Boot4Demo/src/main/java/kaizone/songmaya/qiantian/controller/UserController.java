package kaizone.songmaya.qiantian.controller;

import kaizone.songmaya.qiantian.entity.UserEntity;
import kaizone.songmaya.qiantian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list")
    public List<UserEntity> list() {
        return userService.list();
    }

    @RequestMapping(value = "/add")
    public String add() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("测试");
        userEntity.setAddress("测试地址");
        userEntity.setAge(21);
        userService.save(userEntity);
        return "用户信息添加成功";
    }

    @RequestMapping(value = "/delete")
    public String delete(Long userId) {
        userService.delete(userId);
        return "用户信息删除成功";
    }

    @RequestMapping(value = "/age")
    public List<UserEntity> age() {
        return userService.age();
    }

    /**
     * 根据条件自定义编写删除SQL
     *
     * @return
     */
    @RequestMapping(value = "/deleteWhere")
    public List<UserEntity> deleteWhere(String name, String password) {
        return userService.delete(name, password);
    }

    @RequestMapping(value = "/cutpage")
    public List<UserEntity> cutPage(int page) {
        return userService.cutPage(page);
    }
}
