package kaizone.songmaya.qingtian;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@ComponentScan(basePackages = "kaizone.songmaya.qingtian")
@SpringBootApplication
public class BootSecurity {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(BootSecurity.class, args);
    }
}
