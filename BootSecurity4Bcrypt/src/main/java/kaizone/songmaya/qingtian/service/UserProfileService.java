package kaizone.songmaya.qingtian.service;

import kaizone.songmaya.qingtian.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
