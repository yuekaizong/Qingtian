package kaizone.songmaya.qingtian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqProviderApp {

    static Logger logger = LoggerFactory.getLogger(RabbitmqProviderApp.class);

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProviderApp.class, args);
        logger.info("【【【【【消息队列-消息提供者启动成功.】】】】】");
    }

}
