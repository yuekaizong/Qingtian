package kaizone.songmaya.qingtian.service;

import kaizone.songmaya.qingtian.dao.UserProfileDao;
import kaizone.songmaya.qingtian.jpa.UserProfileJpa;
import kaizone.songmaya.qingtian.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileJpa dao;
//    UserProfileDao dao;

    public List<UserProfile> findAll() {
        return dao.findAll();
    }

    public UserProfile findByType(String type) {
        return dao.findByType(type);
    }

    public UserProfile findById(int id) {
        return dao.findById(id);
    }
}