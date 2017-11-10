package kaizone.songmaya.qingtian.servlet;

import kaizone.songmaya.qingtian.servlet.page.TestServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {

    @Bean
    public ServletRegistrationBean testBean() {
        return new ServletRegistrationBean(new TestServlet(), "/test");
    }
}
