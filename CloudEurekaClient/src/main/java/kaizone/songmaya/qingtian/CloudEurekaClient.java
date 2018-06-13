package kaizone.songmaya.qingtian;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    private static final Logger logger = Logger.getLogger(CloudEurekaClient.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClient.class, args);
    }

    @Value("${server.port}")
    String port;

    @Autowired
    private RestTemplate restTemplate;

//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    @RequestMapping("hi")
    public String hi() {
        return "hi " + ", i am from port:" + port;
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
    @HystrixCommand(fallbackMethod = "callQError")
    public String callQ() {
        logger.log(Level.INFO, "calling trace other client");
        return restTemplate.getForObject("http://localhost:18763/ya", String.class);
    }

    @RequestMapping("/callQ2")
    @HystrixCommand(fallbackMethod = "callQ2Error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),//指定多久超时，单位毫秒。超时进fallback
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
    })
    public String callQ2() {
        logger.info("--------------------callQ2 start---------------------");
        String str1 = restTemplate.getForObject("http://localhost:18763/ya", String.class);
        logger.info("str1=" + str1);
//        String str2 = restTemplate.getForObject("http://localhost:18765/ka", String.class);
        String str2 = restTemplate.getForObject("http://localhost:18764/youxi", String.class);
        logger.info("str2=" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str1).append("\n");
        sb.append(str2).append("\n");
        logger.info("--------------------callQ2 start---------------------");
        return sb.toString();
    }

    @RequestMapping("/callQ3")
    public String callQ3() {
        logger.log(Level.INFO, "-----------callQ3 start-------------");
        String str1 = restTemplate.getForObject("http://SERVICE-YA/ya", String.class);
        logger.info(String.format("http://SERVICE-YA/sleep %s", str1));
        if (str1 == null) {
            str1 = "http://SERVICE-YA/ya-->null";
        }

        String str2 = restTemplate.getForObject("http://SERVICE-RIBBON/normal", String.class);
        logger.info(String.format("http://SERVICE-RIBBON/normal %s", str2));
        if (str2 == null) {
            str2 = "http://SERVICE-RIBBON/normal-->null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(str1).append("\n");
        sb.append(str2).append("\n");
        logger.log(Level.INFO, "-----------callQ3 end-------------");
        return sb.toString();
    }

    @RequestMapping("/callQ4")
    public String callQ4() {
        logger.log(Level.INFO, "-----------callQ4 start-------------");
        String str1 = restTemplate.getForObject("http://SERVICE-YA/sleep", String.class);
        logger.info(String.format("http://SERVICE-YA/sleep %s", str1));
        String str2 = restTemplate.getForObject("http://SERVICE-RIBBON/normal", String.class);
        logger.info(String.format("http://SERVICE-RIBBON/normal %s", str2));
        StringBuilder sb = new StringBuilder();
        sb.append(str1).append("\n");
        sb.append(str2).append("\n");
        logger.log(Level.INFO, "-----------callQ4 end-------------");
        return sb.toString();
    }

    public String callQError() {
        return String.format("callQ error");
    }

    public String callQ2Error(Throwable t) {
        return String.format("callQ error, %s", t);
    }

    @RequestMapping("/info")
    public String info() {
        logger.log(Level.INFO, "calling trace service-hi");
        return "i'm cloudEurekaClient!!";
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }


}
