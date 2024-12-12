package by.ita.chernook.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    private static final String SERVICE_APP_URL = "http://localhost:8111/business";

    @Bean
    public RestTemplate serviceAppClient() {
        return new RestTemplateBuilder()
                .rootUri(SERVICE_APP_URL)
                .build();
    }
}
