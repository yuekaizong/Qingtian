package kaizone.songmaya.qingtian;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class RestTemplateHystrixInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private MockResponseConfig mockResponseConfig;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        final URI originaUri = request.getURI();
        final String serverName = "RestTemplate";

        ClientHttpResponseImpl run = new ClientHttpResponseImpl() {
            @Override
            public ClientHttpResponse action() {
                try {
                    return execution.execute(request, body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        ClientHttpResponseImpl fallback = new ClientHttpResponseImpl() {
            @Override
            public ClientHttpResponse action() {
                return new MyClientHttpResponse(mockResponseConfig.getMockItems().get(0));

            }
        };

        return new RestTemplateHystrixCommnad(serverName, run, fallback).execute();
    }


    public static class RestTemplateHystrixCommnad extends HystrixCommand<ClientHttpResponse> {
        private final ClientHttpResponseImpl run;
        private final ClientHttpResponseImpl fallback;

        public RestTemplateHystrixCommnad(String name, ClientHttpResponseImpl run, ClientHttpResponseImpl fallback) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.run = run;
            this.fallback = fallback;
        }

        @Override
        protected ClientHttpResponse run() {
            return run.action();
        }

        @Override
        protected ClientHttpResponse getFallback() {
            return fallback.action();
        }
    }

    public interface ClientHttpResponseImpl {
        ClientHttpResponse action();
    }

    final class MyClientHttpResponse implements ClientHttpResponse {

        private final MockResponseConfig.MockItem mockInfo;

        private byte[] body;

        private HttpStatus status;

        private HttpHeaders headers;


        MyClientHttpResponse(MockResponseConfig.MockItem mockInfo) {

            if (mockInfo == null) {
                throw new IllegalArgumentException("mockInfo");
            }
            this.mockInfo = mockInfo;
            init();
        }

        private void init() {
            if (org.apache.commons.lang3.StringUtils.isBlank(this.mockInfo.getStatus())
                    || !org.apache.commons.lang3.StringUtils.isNumeric(this.mockInfo.getStatus().trim())) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.valueOf(Integer.valueOf(this.mockInfo.getStatus().trim()));
            }

            headers = new HttpHeaders();
            if (this.mockInfo.getHeaders() == null || this.mockInfo.getHeaders().size() == 0) {
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
            return IOUtils.toInputStream(this.mockInfo.getBody(), StandardCharsets.UTF_8);
        }

        @Override
        public void close() {

        }
    }


}
