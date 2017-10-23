package kaizone.songmaya.qingtian.jpa;

import kaizone.songmaya.qingtian.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserRepository /* extends JpaRepository<User,Long>*/{
    User findByUserName(String username);
    User findByUserNameOrEmail(String username, String email);
    Long deleteById(Long id);
    Long countByUserName(String username);
    List<User> findByEmailLike(String email);
    User findByUserNameIgnoreCase(String userName);
    List<User> findByUserNameOrderByEmailDesc(String email);

    Page<User> findAll(Pageable pageable);
    Page<User> findByUserName(String username, Pageable pageable);

    User findFirstByOrderByLastnameAsc();
    User findTopByOrderByAgeDesc();
    Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
    List<User> findFirst10ByLastname(String lastname, Sort sort);
    List<User> findTop10ByLastname(String lastname, Pageable pageable);

//    @Modifying
//    @Query("update User u set u.username = ?1 where u.id=?2")
    int modifyByIdAndUserId(String username, Long id);

//    @Transactional
//    @Modifying
//    @Query("delete from User where id = ?1")
    void deleteByUserId(Long id);

//    @Transactional
//    @Query("select u from User u where u.email=?1")
    User findByEmailAddress(String emailAdress);
}
