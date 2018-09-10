package kaizone.songmaya.smartns.jpa.loan;

import kaizone.songmaya.smartns.model.loan.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DictJpa extends JpaRepository<Dict, Long> {

    List<Dict> findByPid(Long pid);

}
