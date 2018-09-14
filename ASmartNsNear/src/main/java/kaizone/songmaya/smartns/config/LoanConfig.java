package kaizone.songmaya.smartns.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "loanConfig")
@Configuration
public class LoanConfig {

    private MineConfig mineConfig = new MineConfig();

    public MineConfig getMineConfig() {
        return mineConfig;
    }

    public void setMineConfig(MineConfig mineConfig) {
        this.mineConfig = mineConfig;
    }

    public static class MineConfig {
        private String title;
        private String detail;
        private List<Function> functions = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public List<Function> getFunctions() {
            return functions;
        }

        public void setFunctions(List<Function> functions) {
            this.functions = functions;
        }

        public static class Function {
            private String name;
            private String type;
            private String uri;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
