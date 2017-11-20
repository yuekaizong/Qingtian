package kaizone.songmaya.qiantian.service;

import kaizone.songmaya.qiantian.entity.UserEntity;
import kaizone.songmaya.qiantian.event.UserRegisterEvent;
import kaizone.songmaya.qiantian.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @CacheConfig: 改注释是用来开启声明的类参与缓存，如果方法内的@Cacheable注释没有
 * 添加key值，那么会自动使用cacheNames配置参数，并且追加方法名
 */

@Service
@CacheConfig(cacheNames = "user")
public class UserService implements UserDetailsService{

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserJPA userJPA;

    //@Cacheable：配置方法的缓存参数，可自定义缓存的key以及value
    @Cacheable
    public List<UserEntity> list() {
        return userJPA.findAll();
    }

    public void save(UserEntity entity) {
        userJPA.save(entity);
    }

    public void delete(Long userId) {
        userJPA.delete(userId);
    }

    public List<UserEntity> age() {
        return userJPA.nativeQuery(20);
    }

    public List<UserEntity> delete(String name, String password) {
        userJPA.deleteQuery(name, password);
        return userJPA.findAll();
    }

    public List<UserEntity> cutPage(int page) {
        UserEntity user = new UserEntity();
        user.setSize(2);
        user.setSord("desc");
        user.setPage(page);

        //获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //设置排序对象参数
        Sort sort = new Sort(sort_direction, user.getSidx());
        //创建分页对象
        PageRequest pageRequest = new PageRequest(user.getPage() - 1, user.getSize(), sort);
        //执行分页查询
        return userJPA.findAll(pageRequest).getContent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJPA.findByName(username);
        if (user == null){
            throw new UsernameNotFoundException("未查询到用户："+username+"信息！");
        }
        return user;
    }

    public void register(UserEntity user){
        applicationContext.publishEvent(new UserRegisterEvent(this, user));
    }
}
