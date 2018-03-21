package kaizone.songmaya.qiantian.controller;

import kaizone.songmaya.qiantian.entity.GoodEntity;
import kaizone.songmaya.qiantian.jpa.GoodJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("good")
public class GoodController {

    @Autowired
    private GoodJPA goodJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/index")
    public String index(String name) {
        return "this is index page " + name;
    }

    @RequestMapping(value = "/all")
    public List<GoodEntity> all() {
        return goodJPA.findAll();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public GoodEntity selectOne(Long id) {
        return goodJPA.findOne(id);
    }
}
