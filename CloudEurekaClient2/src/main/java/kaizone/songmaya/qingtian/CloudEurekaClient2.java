package kaizone.songmaya.qingtian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class CloudEurekaClient2 {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient2.class, args);
    }

    private static final Logger LOG = Logger.getLogger(CloudEurekaClient2.class.getName());

    @Value("${server.port}")
    String port;

    @RequestMapping("ya")
    public String ya() {
        LOG.log(Level.INFO, "hi is being called");
        return "这个丫丫的 port:" + port;
    }

    @RequestMapping(value = "sleep")
    public String sleep() {
        try {
            Thread.currentThread().sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("this is sleep, %s", Thread.currentThread().getName());
    }

    @RequestMapping("la")
    public String go() {
        return "lalalala yaka " + port;
    }

    @RequestMapping("/callCloudEurekaClientInfo")
    public String callCloudEurekaClientInfo() {
        LOG.log(Level.INFO, "info is being called");
        return restTemplate.getForObject("http://localhost:18762/info",String.class);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }

}
