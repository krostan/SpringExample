package com.luv2code.springboot.demosecurity.security;

import com.luv2code.springboot.demosecurity.service.UserService;
import com.luv2code.springboot.demosecurity.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // anyRequest().authenticated 任何進入應用程序的請求 都必須經過身分驗證
        // loginProcessingUrl 實際提交表單數據的地方
        // permitAll 每個人都可訪問
        // /authenticateTheUser 不需寫 spring 會提供
        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers("/").hasRole("EMPLOYEE")
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout-> logout.permitAll()
                )
                .exceptionHandling(configurer->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    //    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails john = User.builder()
//                .username("john")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//
//        UserDetails mary = User.builder()
//                .username("mary")
//                .password("{noop}test123")
//                .roles("EMPLOYEE", "MANAGER")
//                .build();
//
//        UserDetails susan = User.builder()
//                .username("susan")
//                .password("{noop}test123")
//                .roles("EMPLOYEE", "MANAGER", "ADMIN")
//                .build();
//
//        // 創建InMemoryUserDetailsManager 的實例
//        return new InMemoryUserDetailsManager(john, mary, susan);
//    }


    // add support for JDBC ... no more hardcoded users
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        // 將資料庫注入
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        // define query to retrieve a user by username
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "SELECT user_id, pw, active FROM members WHERE user_id = ?");
//
//        // define query to retrieve the authorities/roles by username
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT user_id, role FROM roles WHERE user_id = ?");
//
//        return jdbcUserDetailsManager;
//    }
}


