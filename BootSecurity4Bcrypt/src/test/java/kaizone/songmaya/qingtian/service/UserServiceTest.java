package kaizone.songmaya.qingtian.service;

import kaizone.songmaya.qingtian.BcryptApp;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BcryptApp.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class})
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void save() {
    }

    @Test
    public void findById() {
        System.out.println(service.findById(1));
    }

    @Test
    public void findBySso() {
    }
}