package kaizone.songmaya.qiantian.base;

import org.springframework.web.bind.annotation.*;


/**
 * @ControllerAdvice 用来配置控制器通知，可以配置过滤拦截具体一种或者多种类型的注释
 * 添加annotations 熟悉即可，因为我们全局放回Json,所以需要类上配置@ResponseBody
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestExceptionHandler {

    /**
     * 默认统一异常处理方法
     *
     * @ExceptionHandler 用来配置需要拦截的异常类型
     * @ResponseStatus 用于配置遇到异常后返回数据时的StatusCode的值，
     * @param e 默认Exception异常对象
     * @return
     */
    @ExceptionHandler
    @ResponseStatus
    public ApiResult runtimeExceptionHandler(Exception e) {
        return ApiResultGenerator.errorResult(e.getMessage(), e);
    }
}
