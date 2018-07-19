package kaizone.songmaya.xuanguan.service;

import kaizone.songmaya.xuanguan.model.User;

public interface UserService {

    void save(User user);

    User findById(int id);

    User findBySso(String sso);

}