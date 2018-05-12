package kaizone.songmaya.qingtian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqConsumerNode2App {

    static Logger logger = LoggerFactory.getLogger(RabbitmqConsumerNode2App.class);

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqConsumerNode2App.class,args);
        logger.info("【【【【【消息队列-消息消费者节点2启动成功.】】】】】");
    }
}
