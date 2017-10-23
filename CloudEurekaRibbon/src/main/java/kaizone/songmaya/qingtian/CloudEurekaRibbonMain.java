package kaizone.songmaya.qingtian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudEurekaRibbonMain {

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaRibbonMain.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate resetTemplate() {
        return new RestTemplate();
    }
}
