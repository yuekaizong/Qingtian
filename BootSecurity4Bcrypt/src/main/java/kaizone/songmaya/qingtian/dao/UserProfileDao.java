package kaizone.songmaya.qingtian.dao;

import kaizone.songmaya.qingtian.model.UserProfile;

import java.util.List;

public interface UserProfileDao {
    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
