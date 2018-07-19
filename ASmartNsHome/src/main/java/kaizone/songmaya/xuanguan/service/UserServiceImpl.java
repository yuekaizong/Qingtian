package kaizone.songmaya.xuanguan.service;

import kaizone.songmaya.xuanguan.jpa.UserJpa;
import kaizone.songmaya.xuanguan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserJpa dao;
//    private UserDao dao;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findBySso(String sso) {
        return dao.findBySsoId(sso);
    }

}
