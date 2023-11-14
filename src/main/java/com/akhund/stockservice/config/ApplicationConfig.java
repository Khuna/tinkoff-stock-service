package com.akhund.stockservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;


@Configuration
@EnableConfigurationProperties({ApiConfig.class})
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ApiConfig apiConfig;

    @Bean
    public InvestApi api() {
        return InvestApi.createSandbox(System.getenv("ssoToken"));
    }
}
