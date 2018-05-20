package kaizone.songmaya.qingtian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CloudEurekaServerApp2 {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaServerApp2.class, args);
    }
}
