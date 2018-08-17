package kaizone.songmaya.smartns.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public LoginFailureHandler() {
        this.setDefaultFailureUrl("/login?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //保存登陆日志
        //saveLoginLog(request)
        super.onAuthenticationFailure(request, response, exception);
    }
}
