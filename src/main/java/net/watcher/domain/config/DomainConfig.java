package net.watcher.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * DomainConfig config domain beans
 *
 * @author Kostia
 *
 */
@Configuration
public class DomainConfig {
    @Bean
    public RestTemplate crateRestTemplate() {
        return new RestTemplate();
    }
}
