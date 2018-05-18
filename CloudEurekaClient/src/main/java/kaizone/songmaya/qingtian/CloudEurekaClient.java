package kaizone.songmaya.qingtian;

import com.netflix.discovery.converters.Auto;
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
public class CloudEurekaClient {
    private static final Logger LOG = Logger.getLogger(CloudEurekaClient.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient.class, args);
    }

    @Value("${server.port}")
    String port;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("hi")
    public String hi(@RequestParam String name) {
        return "hi " + name + ", i am from port:" + port;
    }

    @RequestMapping("go")
    public String go(@RequestParam String name) {
        return name + "le's go" + port;
    }

    @RequestMapping("/callCloudEurekaClient2Ya")
    public String callQ() {
        LOG.log(Level.INFO, "calling trace other client");
        return restTemplate.getForObject("http://localhost:18763/ya", String.class);
    }

    @RequestMapping("/info")
    public String info(){
        LOG.log(Level.INFO, "calling trace service-hi");
        return "i'm cloudEurekaClient!!";
    }

    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }



}
