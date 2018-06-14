package kaizone.songmaya.qingtian;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
public class CloudEurekaClient2 {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient2.class, args);
    }

    private static final Logger logger = Logger.getLogger(CloudEurekaClient2.class.getName());

    @Value("${server.port}")
    String port;

    @RequestMapping("ya")
    public String ya() {
        logger.log(Level.INFO, "hi is being called");
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

    @HystrixCommand(fallbackMethod = "callQError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),//指定多久超时，单位毫秒。超时进fallback
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
    })
    @RequestMapping("/callQ1/info")
    public String callQ1() {
        logger.info("-------------callQ1 start ---------------");
        String str = restTemplate.getForObject("http://SERVICE-HI/info", String.class);
        logger.info("str=" + str);
        logger.info("-------------callQ1 end ------------------");
        return str;
    }

    @HystrixCommand(fallbackMethod = "callQError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),//指定多久超时，单位毫秒。超时进fallback
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
    })
    @RequestMapping("/callQ1/info1")
    public String callQ11() {
        logger.info("-------------callQ1 start ---------------");
        String str = restTemplate.getForObject("http://localhost:18762/info", String.class);
        logger.info("str=" + str);
        logger.info("-------------callQ1 end ------------------");
        return str;
    }

    @HystrixCommand(fallbackMethod = "callQError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),//指定多久超时，单位毫秒。超时进fallback
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
    })
    @RequestMapping("/callQ1/hiInfo")
    public String callQ2() {
        logger.info("-------------callQ2 start ---------------");
        String hi = restTemplate.getForObject("http://SERVICE-HI/hi", String.class);
        logger.info("str1=" + hi);
        String info = restTemplate.getForObject("http://SERVICE-HI/info", String.class);
        logger.info("str2=" + info);
        logger.info("-------------callQ2 end ------------------");
        return String.format("%s, %s", hi, info);
    }

    public String callQError() {
        return String.format("callQ1 error");
    }

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

}
