package kaizone.songmaya.qiantian.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PrintTask {

    /**
     * 没小时的10分执行该方法
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 10 * * * *")
    public void cron() throws Exception {
        System.out.println("执行测试cron时间：" + new Date(System.currentTimeMillis()));
    }

    /**
     * 是上一个调用开始后再次调用的延时(不用等待上一次调用完成)
     */
//    @Scheduled(fixedRate = 1000*10)
    public void fixRate() throws Exception {
//        Thread.sleep(2000);
        System.out.println("执行测试fixedRate时间：" + new Date(System.currentTimeMillis()));
    }

    @Scheduled(fixedDelay = 1000 * 10)
    public void fixedDelay() throws Exception {
        System.out.println("执行测试fixDelay时间：" + new Date(System.currentTimeMillis()));
    }

    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 10)
    public void initialDelay() throws Exception {
        System.out.println("执行测试initialDate时间：" + new Date(System.currentTimeMillis()));
    }

}
