package kaizone.songmaya.qingtian;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
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
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
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

    @RequestMapping("divide")
    @HystrixCommand(fallbackMethod = "divideError")
    public String divide(@RequestParam int divisor, @RequestParam int dividend) {
        return String.format("%s/%s=%s", dividend, divisor, dividend / divisor);
    }

    public String divideError(int divisor, int dividend) {
        return "divide Error!!!";
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
    public String info() {
        LOG.log(Level.INFO, "calling trace service-hi");
        return "i'm cloudEurekaClient!!";
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }


}
