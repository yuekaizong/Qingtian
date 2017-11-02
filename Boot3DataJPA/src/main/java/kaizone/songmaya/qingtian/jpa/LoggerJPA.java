package kaizone.songmaya.qingtian.jpa;

import kaizone.songmaya.qingtian.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerJPA extends JpaRepository<LoggerEntity, Long> {
}
