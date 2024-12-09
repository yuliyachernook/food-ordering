package by.ita.chernook.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    private static final String DATA_BASE_APP_URL = "http://localhost:8101/database";

    @Bean
    public RestTemplate dataBaseAppClient() {
        return new RestTemplateBuilder()
                .rootUri(DATA_BASE_APP_URL)
                .build();
    }
}
