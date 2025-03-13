package com.spring.ai.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redirect")
public record RedirectProperties(
        String url
) {
}
