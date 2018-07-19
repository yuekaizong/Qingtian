package kaizone.songmaya.xuanguan.service;

import kaizone.songmaya.xuanguan.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
