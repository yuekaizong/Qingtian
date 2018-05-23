package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.HomeApp;
import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

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

    @Test
    public void home() {
        List<DivEntity> data = divJPA.findAll();
        System.out.println(data);
        Assert.assertNotNull(data);
    }
}