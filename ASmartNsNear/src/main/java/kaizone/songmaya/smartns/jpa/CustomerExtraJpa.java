package kaizone.songmaya.smartns.jpa;

import kaizone.songmaya.smartns.model.CustomerExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerExtraJpa extends JpaRepository<CustomerExtra, Long> {

    CustomerExtra findBySsoId(String ssoId);
}
