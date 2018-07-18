package kaizone.songmaya.qingtian.dao;

import kaizone.songmaya.qingtian.BcryptApp;
import kaizone.songmaya.qingtian.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BcryptApp.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class})
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Test
    public void save() {
    }

    @Test
    public void findById() {
        User obj = dao.findById(1);
        System.out.println(obj);
    }

    @Test
    public void findBySSO() {
    }
}