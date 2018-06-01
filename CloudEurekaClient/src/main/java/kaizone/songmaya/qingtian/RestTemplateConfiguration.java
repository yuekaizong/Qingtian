package kaizone.songmaya.qingtian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(2 * 1000)
                .setReadTimeout(30 * 1000).build();

        RestTemplateHystrixInterceptor hystrixInterceptor = applicationContext.getBean("restTemplateHystrixInterceptor", RestTemplateHystrixInterceptor.class);
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            interceptors = new ArrayList<>();
            restTemplate.setInterceptors(interceptors);
        }
        interceptors.add(hystrixInterceptor);
        return restTemplate;
    }

    @Bean
    ClientHttpRequestInterceptor restTemplateHystrixInterceptor() {
        return new RestTemplateHystrixInterceptor();
    }

}
