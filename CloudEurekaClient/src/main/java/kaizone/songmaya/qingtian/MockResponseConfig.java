package kaizone.songmaya.qingtian;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.net.URI;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "restTemplateMock")
@Configuration
public class MockResponseConfig {

    private List<MockItem> mockItems;

    public List<MockItem> getMockItems() {
        return mockItems;
    }

    public void setMockItems(List<MockItem> mockItems) {
        this.mockItems = mockItems;
    }


    public MockItem getMockConf(URI uri) {
        if (this.getMockItems() == null || this.getMockItems().size() == 0) {
            return null;
        }

        AntPathMatcher pathMatcher = new AntPathMatcher();
        pathMatcher.setCaseSensitive(false);

        String requestPath = uri.getPath();

        for (MockItem item : this.getMockItems()) {
            if (item != null) {
                String patternPath = item.getUrl();
                if (StringUtils.isNotBlank(patternPath)) {
                    if (pathMatcher.match(patternPath, requestPath)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public static class MockItem {
        private String url;
        private String status;
        private Map<String, List<String>> headers;
        private String body;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, List<String>> headers) {
            this.headers = headers;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }


}
