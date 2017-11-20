package kaizone.songmaya.qiantian.listener.annotation;

import kaizone.songmaya.qiantian.entity.UserEntity;
import kaizone.songmaya.qiantian.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class AnnotationRegisterListener {

    @EventListener
    public void register(UserRegisterEvent userRegisterEvent){
        UserEntity user = userRegisterEvent.getUser();

        //../省略逻辑

        //输出注册用户信息
        System.out.println("@EventListener注册信息，用户名："+user.getName()+"，密码："+user.getPassword());
    }
}
