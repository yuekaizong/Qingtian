package kaizone.songmaya.qingtian.controller;

import com.alibaba.fastjson.JSONObject;
import kaizone.songmaya.qingtian.entity.UserEntity;
import kaizone.songmaya.qingtian.jpa.UserJPA;
import kaizone.songmaya.qingtian.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<UserEntity> list() {
        return userJPA.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public UserEntity save(UserEntity entiry) {
        return userJPA.save(entiry);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public List<UserEntity> delete(Long id) {
        userJPA.delete(id);
        return userJPA.findAll();
    }

    @RequestMapping(value = "/login")
    public String login(UserEntity user, HttpServletRequest request)
    {
        //登录成功
        boolean flag = true;
        String result = "登录成功";
        //根据用户名查询用户是否存在
        UserEntity userEntity = userJPA.findOne(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("name"), user.getName()));
                return null;
            }
        });
        //用户不存在
        if(userEntity == null){
            flag = false;
            result = "用户不存在，登录失败";}
        //密码错误
        else if(!userEntity.getPassword().equals(user.getPassword())){
            flag = false;
            result = "用户密码不相符，登录失败";
        }
        //登录成功
        if(flag){
            //将用户写入session
            request.getSession().setAttribute("_session_user",userEntity);

            JSONObject obj = new JSONObject();
            obj.put("msg", "用户："+userEntity.getName()+","+result);
            request.setAttribute(LoggerUtils.LOGGER_RETURN, obj);
        }
        return result;
    }
}
