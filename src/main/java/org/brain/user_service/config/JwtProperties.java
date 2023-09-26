package org.brain.user_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JwtProperties (String secret, String lifetime){
}
