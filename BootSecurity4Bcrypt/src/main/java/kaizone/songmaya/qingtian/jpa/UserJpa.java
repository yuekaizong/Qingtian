package kaizone.songmaya.qingtian.jpa;

import kaizone.songmaya.qingtian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Integer> {

    User findById(int id);

    User findBySsoId(String sso);
}
