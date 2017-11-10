package kaizone.songmaya.qiantian.controller;

import kaizone.songmaya.qiantian.entity.GoodEntity;
import kaizone.songmaya.qiantian.jpa.GoodJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
public class GoodController {

    @Autowired
    private GoodJPA goodJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "query")
    public List<GoodEntity> list(){

        return null;
    }

}
