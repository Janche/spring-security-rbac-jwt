package com.example.janche.web.controller.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lirong
 * @ClassName: CustomConfig
 * @Description: 自定义的配置
 * @date 2018-12-03 9:26
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
