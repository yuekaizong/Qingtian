package kaizone.songmaya.xuanguan.service;

import kaizone.songmaya.xuanguan.entity.User;
import kaizone.songmaya.xuanguan.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class UserService implements UserDetailsService{

    @Autowired
    private UserJpa userJpa;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpa.findByName(username);
        if (user == null){
            throw new UsernameNotFoundException("未查询到用户："+username+"信息！");
        }
        return user;
    }
}
