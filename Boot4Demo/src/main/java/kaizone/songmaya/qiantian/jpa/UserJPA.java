package kaizone.songmaya.qiantian.jpa;

import kaizone.songmaya.qiantian.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserJPA extends JpaRepository<UserEntity, Long>{

    @Query(value = "select * from t_user where t_age > ?1 ", nativeQuery = true)
    public List<UserEntity> nativeQuery(int age);

    @Modifying
    @Query(value = "delete from t_user where t_name = ?1 and t_password = ?2", nativeQuery = true)
    public void deleteQuery(String name, String pwd);

    public UserEntity findByName(String name);

}
