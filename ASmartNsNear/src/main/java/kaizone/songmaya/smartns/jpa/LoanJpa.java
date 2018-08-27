package kaizone.songmaya.smartns.jpa;

import kaizone.songmaya.smartns.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanJpa extends JpaRepository<Loan, Integer> {

    Loan findByName(String name);
}
