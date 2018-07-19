package kaizone.songmaya.xuanguan.jpa;

import kaizone.songmaya.xuanguan.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileJpa extends JpaRepository<UserProfile, Integer> {

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
