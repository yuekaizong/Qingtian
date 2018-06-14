package kaizone.songmaya.qingtian.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
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
        Span current_span =  tracer.getCurrentSpan();
        logger.info(String.format("currentSpan =%s", current_span));
        Span new_span = tracer.createSpan("doSomeWorkNewSpan");
        logger.info(String.format("newSpan =%s", new_span));
        Thread.sleep(1000L);
        new_span.stop();
        logger.info("i'm the original span");
    }

    @Async
    public void asyncMethod() throws InterruptedException{
        logger.info("Start async Method");
        Thread.sleep(1000L);
        logger.info("End Async Method");
    }


}
