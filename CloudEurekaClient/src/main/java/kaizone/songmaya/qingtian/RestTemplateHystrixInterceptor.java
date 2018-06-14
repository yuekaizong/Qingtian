package kaizone.songmaya.qingtian;

import com.netflix.hystrix.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class RestTemplateHystrixInterceptor implements ClientHttpRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockResponseConfig mockResponseConfig;

    @Autowired
    private Tracer tracer;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        final URI requestURI = request.getURI();
        String commandKeyName = mapCommandKey(requestURI);

//        ClientHttpResponse run = execution.execute(request, body);
//        ClientHttpResponse fallback = new MyClientHttpResponse(mockResponseConfig.getMockItems().get(0));
        Supplier<ClientHttpResponse> run = () -> {
            try {
                logger.info(String.format("%s,%s", request.getURI(), request.getMethod()));
                return execution.execute(request, body);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };

        Supplier<ClientHttpResponse> fallback = () -> {
            if (mockResponseConfig.getMockItems() != null && !mockResponseConfig.getMockItems().isEmpty()) {
                return new MyClientHttpResponse(mockResponseConfig.getMockItems().get(0));
            }
            return new MyClientHttpResponse(null);
        };
        return new RestTemplateHystrixCommnad(request, run, fallback).execute();
    }

    public static String mapCommandKey(URI uri) {
        if (uri == null)
            return null;
        if (StringUtils.isEmpty(uri.getPath()))
            return null;
        return StringUtils.substringAfterLast(uri.getPath(), "/");
    }

    public static String threadPoolKeyName(URI uri) {
        if (uri == null)
            return null;
        if (StringUtils.isEmpty(uri.getPath()))
            return null;
        String str1 = StringUtils.substringAfter(uri.getPath(), "appserver/");
        return StringUtils.substringBefore(str1, "/");
    }

    public static void main(String[] args) {
        URI uri = URI.create("http://localhost:18764/app/appserver/crm/cust/yayaya");
        System.out.println(mapCommandKey(uri));
        System.out.println(threadPoolKeyName(uri));
    }

    public static class RestTemplateHystrixCommnad extends HystrixCommand<ClientHttpResponse> {
        private final Supplier<ClientHttpResponse> run;
        private final Supplier<ClientHttpResponse> fallback;
        private final HttpRequest request;

        public RestTemplateHystrixCommnad(HttpRequest request, Supplier<ClientHttpResponse> run, Supplier<ClientHttpResponse> fallback) {
//            super(ApiSetter.setter(commandKeyName));
            super(ApiSetter.semaphoreSetter(request.getURI(), request.getMethod()));
            this.request = request;
            this.run = run;
            this.fallback = fallback;
        }

        @Override
        protected ClientHttpResponse run() {
            ClientHttpResponse response = run.get();
            if (response == null) {
                throw new RuntimeException(String.format("No response:%s %s", this.request.getMethod(), this.request.getURI()));
            }
            return response;
        }

        @Override
        protected ClientHttpResponse getFallback() {
            if (this.fallback != null) {
                return this.fallback.get();
            } else {
                return super.getFallback();
            }
        }
    }

    /**
     * 调用API设置的参数或公共参数
     */
    /**
     * 调用API设置的参数或公共参数
     */
    private static class ApiSetter {

        public static HystrixCommand.Setter threadSetter(URI url, HttpMethod method) {

            //服务分组
            HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey(url.getHost());
            //服务标识
            HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey(url.getPath() + "|" + method.name());
            //线程池名称
            HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey(url.getHost());
            //线程配置
            HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                    .withCoreSize(25)
                    .withKeepAliveTimeMinutes(5)
                    .withMaxQueueSize(Integer.MAX_VALUE)
                    .withQueueSizeRejectionThreshold(10000);

            //命令属性的配置
            HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                    .withExecutionIsolationThreadInterruptOnTimeout(true)
                    .withExecutionTimeoutInMilliseconds(5 * 1000) //设置自动熔断超时时间
                    .withCircuitBreakerErrorThresholdPercentage(20);//失败率达到20%自动熔断

            //返回
            return HystrixCommand.Setter
                    .withGroupKey(groupKey)
                    .andCommandKey(commandKey)
                    .andThreadPoolKey(threadPoolKey)
                    .andThreadPoolPropertiesDefaults(threadPoolProperties)
                    .andCommandPropertiesDefaults(commandProperties);
        }

        /**
         * ☆参数说明：
         * 1.HystrixCommandGroupKey：服务分组，以上groupKey分组就包括多个服务，必填选项
         * <p>
         * 2.HystrixCommandKey：服务的名称，唯一标识，如果不配置，则默认是类名
         * <p>
         * 3.HystrixThreadPoolKey：线程池的名称，相同线程池名称的线程池是同一个，如果不配置，默认为分组名
         * <p>
         * 4.HystrixThreadPoolProperties：线程池的配置，
         * coreSize配置核心线程池的大小，
         * maxQueueSize线程池队列的最大大小，
         * queueSizeRejectionThreshold，限制当前队列的大小，
         * 实际队列大小由这个参数决定，即到达队列里面条数到达10000，则都会被拒绝。
         * <p>
         * 5.HystrixCommandProperties：配置命令的一些参数，
         * 如executionIsolationStrategy，配置执行隔离策略，默认是使用线程隔离，THREAD即为线程池隔离，
         * <p>
         * ExecutionIsolationThreadInterruptOnTimeout 使用线程隔离时,是否对命令执行超时的线程调用中断操作.默认：true
         * 和ExecutionTimeoutInMilliseconds配置了启用超时和最大执行时间，这里为3s，
         * <p>
         * circuitBreakerErrorThresholdPercentage失败率配置，默认为50%，
         * 这里配置的为25%，即失败率到达25%触发熔断
         */


        public static HystrixCommand.Setter semaphoreSetter(URI url, HttpMethod method) {
            return HystrixCommand.Setter
                    //设置GroupKey 用于dashboard 分组展示
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(url.getHost()))
                    //设置CommandKey 用于Semaphore分组，相同的CommandKey属于同一组隔离资源
                    .andCommandKey(HystrixCommandKey.Factory.asKey(url.getPath() + "|" + method.name()))
                    //设置隔离级别：Semaphore
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            //是否开启熔断器机制
                            .withCircuitBreakerEnabled(true)
                            //舱壁隔离策略
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                            //设置每组command可以申请的permit最大数
                            .withExecutionIsolationSemaphoreMaxConcurrentRequests(50)
                            //circuitBreaker打开后多久关闭
                            .withCircuitBreakerSleepWindowInMilliseconds(5000));
        }
    }


    private final class MyClientHttpResponse implements ClientHttpResponse {

        private final MockResponseConfig.MockItem mockInfo;

        private byte[] body;

        private HttpStatus status;

        private HttpHeaders headers;


        MyClientHttpResponse(MockResponseConfig.MockItem mockInfo) {
            this.mockInfo = mockInfo;
            init();
        }

        private void init() {
            if (mockInfo == null) {
                status = HttpStatus.OK;
            } else if (StringUtils.isBlank(this.mockInfo.getStatus())
                    || StringUtils.isNumeric(this.mockInfo.getStatus().trim())) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.valueOf(Integer.valueOf(this.mockInfo.getStatus().trim()));
            }

            headers = new HttpHeaders();
            if (mockInfo == null) {
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            } else if (this.mockInfo.getHeaders() == null || this.mockInfo.getHeaders().size() == 0) {
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            } else {
                headers.putAll(this.mockInfo.getHeaders());
            }
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return status;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return status.value();
        }

        @Override
        public String getStatusText() throws IOException {
            return status.getReasonPhrase();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }

        @Override
        public InputStream getBody() throws IOException {
            if (mockInfo == null) {
                return null;
            }
            return IOUtils.toInputStream(this.mockInfo.getBody(), StandardCharsets.UTF_8);
        }

        @Override
        public void close() {

        }
    }


}
