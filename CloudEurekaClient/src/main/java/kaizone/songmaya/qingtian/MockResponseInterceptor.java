package kaizone.songmaya.qingtian;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class MockResponseInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockResponseInterceptor.class);

    @Autowired
    private MockResponseConfig config;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        MockResponseConfig.MockItem mockInfo = this.getMockConf(request.getURI());
        if (!this.needToMock(mockInfo)) {
            return execution.execute(request, body);
        }

        ClientHttpResponse response = new MockedHttpResponse(mockInfo);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Mock Response: {}, request URI: {}, mocked info: {}",
                    request.getMethod(),
                    request.getURI(),
                    JSON.toJSONString(mockInfo));
        }
        return response;
    }


    private boolean needToMock(MockResponseConfig.MockItem mockInfo) {
        return mockInfo != null;
    }

    private MockResponseConfig.MockItem getMockConf(URI uri) {
        if (this.config != null) {
            return this.config.getMockConf(uri);
        } else {
            return null;
        }
    }


    final class MockedHttpResponse implements ClientHttpResponse {

        private final MockResponseConfig.MockItem mockInfo;

        private byte[] body;

        private HttpStatus status;

        private HttpHeaders headers;


        MockedHttpResponse(MockResponseConfig.MockItem mockInfo) {

            if (mockInfo == null) {
                throw new IllegalArgumentException("mockInfo");
            }
            this.mockInfo = mockInfo;
            init();
        }

        private void init() {
            if (StringUtils.isBlank(this.mockInfo.getStatus())
                    || !StringUtils.isNumeric(this.mockInfo.getStatus().trim())) {
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

