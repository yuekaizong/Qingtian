package kaizone.songmaya.qingtian.dao;

import kaizone.songmaya.qingtian.model.User;

public interface UserDao {
    void save(User user);

    User findById(int id);

    User findBySSO(String sso);
}
