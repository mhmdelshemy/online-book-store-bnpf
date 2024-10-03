package com.bookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bookstore.api")
@Data
public class BookstoreConfig {
    private String cartMapping;
}
