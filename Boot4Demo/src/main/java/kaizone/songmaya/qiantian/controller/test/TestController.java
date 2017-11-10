package kaizone.songmaya.qiantian.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/log/off")
    public String index(){
        logger.debug("Test -> 记录debug日志");
        logger.info("Test -> 访问了index方法");
        logger.error("Test -> 记录error错误日志");
        return "这是一个利用logging.level.包名 = \"off\" 来关闭日志";
    }
}
