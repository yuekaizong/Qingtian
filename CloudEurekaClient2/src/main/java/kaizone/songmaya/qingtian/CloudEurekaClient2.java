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
public class CloudEurekaClient2 {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient2.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("ya")
    public String ya() {
        return "这个丫丫的 port:" + port;
    }

    @RequestMapping("la")
    public String go() {
        return   "lalalala yaka " + port;
    }
}
