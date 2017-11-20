package kaizone.songmaya.qiantian.listener.disorder;

import kaizone.songmaya.qiantian.entity.UserEntity;
import kaizone.songmaya.qiantian.event.UserRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class RegisterListener implements ApplicationListener<UserRegisterEvent>{

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        UserEntity user = event.getUser();

        System.out.println("注册信息，用户名："+user.getName()+"，密码："+user.getPassword());
    }
}
