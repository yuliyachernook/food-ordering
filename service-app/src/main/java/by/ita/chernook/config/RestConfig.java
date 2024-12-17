package by.ita.chernook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Value("${database.api.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate dataBaseAppClient() {
        return new RestTemplateBuilder()
                .rootUri(baseUrl)
                .build();
    }
}
