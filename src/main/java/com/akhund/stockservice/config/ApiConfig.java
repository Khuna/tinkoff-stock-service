package com.akhund.stockservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
@Data
public class ApiConfig {

    private Boolean isSandboxMode;
}
