package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.HomeApp;
import kaizone.songmaya.xuanguan.entity.DivCellEntity;
import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivCellJPA;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeApp.class)
@WebAppConfiguration
@TestExecutionListeners(
        {
                DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class
        }
)
public class IndexControllerTest {

    @Autowired
    private DivJPA divJPA;

    @Autowired
    private DivCellJPA divCellJPA;

    @Test
    public void home() {
        List<DivEntity> data = divJPA.findAll();
        System.out.println(data);
        Assert.assertNotNull(data);
    }

    @Test
    public void test1() {
        String name = "名";
        Specification<DivCellEntity> specification = new Specification<DivCellEntity>() {
            @Override
            public Predicate toPredicate(Root<DivCellEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name").as(String.class), name);
            }
        };

        int page = 0;
        int size = 4;
        Pageable pageable = new PageRequest(page, size);
        Page<DivCellEntity> data = divCellJPA.findAll(specification, pageable);
        data.getContent().stream().forEach(action -> System.out.println(String.format("%s,%s", action.getId(), action.getName())));
    }

    @Test
    public void test2() {
        String name = "名";
        int pid = 2;
        Specification<DivCellEntity> specification = new Specification<DivCellEntity>() {
            @Override
            public Predicate toPredicate(Root<DivCellEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
                list.add(cb.equal(root.get("pid").as(Long.class), pid));

                Predicate[] p = new Predicate[list.size()];
                query.where(cb.and(list.toArray(p)));
                query.distinct(true);//去除重复的结果（多对多可能产生重复的结果）
                query.orderBy(cb.desc(root.get("id").as(Long.class))); //添加排序的功能
                return query.getRestriction();
            }
        };

        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest(page, size);
        Page<DivCellEntity> data = divCellJPA.findAll(specification, pageable);
        data.getContent().stream().forEach(action -> System.out.println(String.format("%s,%s", action.getId(), action.getName())));
    }
}