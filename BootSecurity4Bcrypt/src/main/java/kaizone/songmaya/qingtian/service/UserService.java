package kaizone.songmaya.qingtian.service;

import kaizone.songmaya.qingtian.model.User;

public interface UserService {

    void save(User user);

    User findById(int id);

    User findBySso(String sso);

}