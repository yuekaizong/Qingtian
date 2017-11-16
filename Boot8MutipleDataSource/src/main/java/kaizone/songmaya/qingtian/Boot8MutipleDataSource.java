package kaizone.songmaya.qingtian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Boot8MutipleDataSource {
    public static void main(String[] args) {
        SpringApplication.run(Boot8MutipleDataSource.class, args);
    }

    private static class ServletInitializer extends SpringBootServletInitializer {
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(Boot8MutipleDataSource.class);
        }
    }
}
