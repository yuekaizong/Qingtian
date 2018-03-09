package kaizone.songmaya.moo.base.jpa;

import kaizone.songmaya.moo.base.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserJPA extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);
}
