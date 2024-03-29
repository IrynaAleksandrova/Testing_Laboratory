package com.example.testing_laboratory.config;

import com.example.testing_laboratory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserService userService;

    private static final String[] PUBLIC_URLS = {
            "/",
            "/clientInfo",
            "/selectClient",
            "/clientLog"
    };
    private static final String[] ADMIN_AND_HEAD_URLS = {
            "user/addEmployee",
            "user/{id}/deleteEmployee",
            "user/addClient",
            "user/{id}/deleteClient"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers("/user/addDepartment").hasAuthority("ADMIN")
                .antMatchers(ADMIN_AND_HEAD_URLS).hasAnyAuthority("ADMIN", "HEAD_OF_DEPARTMENT")
                .antMatchers("user/selectProcess", "user/stopProcess").hasAnyAuthority("DEPARTMENT_WORKER", "SERVICE_ENGINEER")
                .antMatchers("user/showClientList").hasAnyAuthority("ADMIN", "HEAD_OF_DEPARTMENT", "DEPARTMENT_WORKER", "SERVICE_ENGINEER")
                .antMatchers("/user/cleaning","/user/production").hasAuthority("DEPARTMENT_WORKER")
                .antMatchers("/user/delivery_report","/user/payment").hasAuthority("HEAD_OF_DEPARTMENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    }
}
