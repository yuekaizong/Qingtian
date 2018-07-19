package kaizone.songmaya.xuanguan.jpa;

import kaizone.songmaya.xuanguan.HomeApp;
import kaizone.songmaya.xuanguan.model.User;
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
@SpringBootTest(classes = HomeApp.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class})
public class UserJpaTest {

    @Autowired
    UserJpa userJpa;

    @Test
    public void findByName(){
        User user = userJpa.findBySsoId("admin");
        System.out.println(user);
    }

}
