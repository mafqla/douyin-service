package com.yali.vilivili.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurity配置
 *
 * @author fuqianlin
 * @date 2023-01-06 17:25
 **/

@Configuration
public class WebSecurityConfig {


    /**
     * 配置Spring Security的http安全
     *
     * @throws Exception 异常
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 基于token，不需要csrf
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize

                                //springboot2.7写法
                                .antMatchers("/user/**").permitAll()
                                .antMatchers("/user/updateAndSaveUser").permitAll()
                                .antMatchers("/auth/login").permitAll()
                                .antMatchers("/user/register").permitAll()
                                .antMatchers("/static/**", "/resources/**").permitAll()
                                .antMatchers("/swagger/**").permitAll()
                                .antMatchers("/swagger-ui.html").permitAll()
                                .antMatchers("/webjars/**").permitAll()
                                .antMatchers("/v2/**").permitAll()
                                .antMatchers("/v2/api-docs-ext/**").permitAll()
                                .antMatchers("/swagger-resources/**").permitAll()
                                .antMatchers("/doc.html").permitAll()
                                .antMatchers("/util/**").permitAll()
                                .antMatchers("/img/**").permitAll()
                              //springboot3.0写法
//                                .requestMatchers("/user/**").permitAll()
//                                .requestMatchers("/user/updateAndSaveUser").permitAll()
//                                // 放行登录接口
//                                .requestMatchers("/user/login").permitAll()
//                                // 放行注册接口
//                                .requestMatchers("/user/register","").permitAll()
//                                // 放行资源目录
//                                .requestMatchers("/static/**", "/resources/**").permitAll()
                                // 其余的都需要权限校验
                                .anyRequest().authenticated()
                                // 防跨站请求伪造
                                .and().csrf(AbstractHttpConfigurer::disable);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).build();
    }


}


