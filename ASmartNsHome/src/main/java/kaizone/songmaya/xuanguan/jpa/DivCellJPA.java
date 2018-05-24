package kaizone.songmaya.xuanguan.jpa;

import kaizone.songmaya.xuanguan.entity.DivCellEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DivCellJPA extends PagingAndSortingRepository<DivCellEntity, Long>, JpaSpecificationExecutor {
}
