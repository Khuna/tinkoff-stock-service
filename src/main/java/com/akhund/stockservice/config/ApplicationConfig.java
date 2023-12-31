package com.akhund.stockservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

@Configuration
@EnableConfigurationProperties({ApiConfig.class})
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ApiConfig apiConfig;

    @Bean
    public OpenApi api() {
        return new OkHttpOpenApi(System.getenv("ssoToken"), apiConfig.getIsSandboxMode());
    }
}
