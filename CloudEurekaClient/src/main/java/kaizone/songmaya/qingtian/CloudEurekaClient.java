package kaizone.songmaya.qingtian;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class CloudEurekaClient {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("hi")
    public String hi(@RequestParam String name) {
        return "hi " + name + ", i am from port:" + port;
    }

    @RequestMapping("go")
    public String go(@RequestParam String name) {
        return  name + "le's go" + port;
    }
}
