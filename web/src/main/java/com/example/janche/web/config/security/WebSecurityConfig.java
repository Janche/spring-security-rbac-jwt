package com.example.janche.web.config.security;

import com.example.janche.web.controller.config.CustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    @Qualifier("securityAccessDeniedHandler")
    private AccessDeniedHandler securityAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                // 关闭 CSRF
                .and().csrf().disable()
                // 登录行为由自己实现，参考 AuthController#login
                .formLogin().disable()
                .httpBasic().disable()

                // 认证请求
                .authorizeRequests()
                // 所有请求都需要登录访问
                .anyRequest()
                .authenticated()
                // RBAC 动态 url 认证
                .anyRequest()
                .access("@rbacAuthorityService.hasPermission(request,authentication)")

                // 登出行为由自己实现，参考 AuthController#logout
                .and().logout().disable()
                // 异常处理
                .exceptionHandling().accessDeniedHandler(securityAccessDeniedHandler);

        // Session 管理
        http.sessionManagement()
                // 因为使用了JWT，所以这里不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code http.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity and = web.ignoring().and();

        // 忽略 GET
        customConfig.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        customConfig.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        customConfig.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        customConfig.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 设置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
