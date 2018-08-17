package kaizone.songmaya.smartns.jpa;

import kaizone.songmaya.smartns.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerJpa extends JpaRepository<Customer, Long> {

    Customer findByUsername(String name);
}
