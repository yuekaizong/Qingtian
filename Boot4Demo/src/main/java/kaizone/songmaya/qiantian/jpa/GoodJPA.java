package kaizone.songmaya.qiantian.jpa;

import kaizone.songmaya.qiantian.entity.GoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface GoodJPA extends JpaRepository<GoodEntity, Long>{
}
