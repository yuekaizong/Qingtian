package kaizone.songmaya.qingtian.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SleuthService {

    Logger logger = LoggerFactory.getLogger(SleuthService.class);
    private final Tracer tracer;

    @Autowired
    public SleuthService(Tracer tracer){
        this.tracer = tracer;
    }

    public void doSomeWorkSameSpan() throws InterruptedException{
        Thread.sleep(1000L);
        logger.info("Doing Some work");
    }

    public void doSomeWorkNewSpan() throws InterruptedException{
        logger.info("i'm the original span");
    }

    @Async
    public void asyncMethod() throws InterruptedException{
        logger.info("Start async Method");
        Thread.sleep(1000L);
        logger.info("End Async Method");
    }


}
