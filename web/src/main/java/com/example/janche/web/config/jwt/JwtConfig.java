package com.example.janche.web.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>
 * JWT 配置
 * </p>
 */

@Component
@ConfigurationProperties(prefix = "janche.jwt")
@Data
public class JwtConfig {
    /**
     * jwt 加密 key，默认值：janche.
     */
    private String secret = "janche";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
