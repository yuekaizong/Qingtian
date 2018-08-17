package kaizone.songmaya.smartns.service;

import kaizone.songmaya.smartns.model.Customer;
import kaizone.songmaya.smartns.jpa.CustomerJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class CustomerService implements UserDetailsService{

    @Autowired
    private CustomerJpa userJpa;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userJpa.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("未查询到用户："+username+"信息！");
        }
        return user;
    }
}
