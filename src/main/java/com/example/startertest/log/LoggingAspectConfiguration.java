package com.example.startertest.log;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;



@Getter
public class LoggingAspectConfiguration {
    private final Logger logger;
    private final Level logLevel;

    public LoggingAspectConfiguration(Class<?> clazz, String level) {
        logger = LoggerFactory.getLogger(clazz);
        logLevel = createLogginLevel(level);
    }

    public void logOut(String message) {
        logger.atLevel(logLevel).log(message);
    }

    public void logOut(String message, Level logLevel) {
        logger.atLevel(logLevel).log(message);
    }


    private Level createLogginLevel(String level) {
        return switch (level.toUpperCase()) {
            case "DEBUG" -> Level.DEBUG;
            case "INFO" -> Level.INFO;
            case "WARN" -> Level.WARN;
            case "ERROR" -> Level.ERROR;
            case "TRACE" -> Level.TRACE;
            default -> throw new RuntimeException("Unknown log level: " + level);
        };
    }

}
