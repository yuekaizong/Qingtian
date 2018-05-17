package kaizone.songmaya.qingtian.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
    }

    @HystrixCommand(fallbackMethod = "goError")
    public String goService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/go?name=" + name, String.class);
    }

    @HystrixCommand(fallbackMethod = "error")
    public String yaya() {
        return restTemplate.getForObject("http://SERVICE-ya/ya", String.class);
    }

    public String yala() {
        return restTemplate.getForObject("http://SERVICE-ya/la", String.class);
    }

    public String hiError(String name) {
        return "hi, "+name+", sorry, error!";
    }

    public String goError(String name) {
        return "go, "+name+", sorry, error!";
    }

    public String error(){
        return "这是一个错误";
    }
}
