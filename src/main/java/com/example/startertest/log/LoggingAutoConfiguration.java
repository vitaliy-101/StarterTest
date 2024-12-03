package com.example.startertest.log;

import com.example.startertest.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAutoConfiguration {
    private final LoggingProperties loggingProperties;

    public LoggingAutoConfiguration(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "loggingt1.enabled", havingValue = "true")
    public LoggingAspect loggingAspect() {
        if (loggingProperties.getLevel() == null || loggingProperties.getLevel().isEmpty()) {
            loggingProperties.setLevel("INFO");
        }
        return new LoggingAspect(loggingProperties);
    }

}
