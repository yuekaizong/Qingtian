package kaizone.songmaya.qiantian.event;

import kaizone.songmaya.qiantian.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

public class UserRegisterEvent extends ApplicationEvent{

    private UserEntity user;

    public UserRegisterEvent(Object source, UserEntity user) {
        super(source);
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
