package kaizone.songmaya.moo.base.config;

import kaizone.songmaya.moo.base.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .anyRequest().authenticated()//所有请求必须登陆后访问
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/shell/edit").hasRole("ADMIN")
                .antMatchers("/shell/save").hasRole("ADMIN")
                .antMatchers("/shell/list").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/index/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()//登录界面，错误界面可以直接访问
                .and()
                .logout()
                .permitAll();//注销请求可直接访问

    }
}
