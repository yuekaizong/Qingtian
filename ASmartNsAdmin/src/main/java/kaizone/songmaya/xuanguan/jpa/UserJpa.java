package kaizone.songmaya.xuanguan.jpa;

import kaizone.songmaya.xuanguan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserJpa extends JpaRepository<User, Long> {

    User findByName(String name);
}
