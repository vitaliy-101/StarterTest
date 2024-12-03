package com.example.startertest.log;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties(prefix = "loggingt1")
public class LoggingProperties {
    private Boolean enabled;
    private String level;

}
