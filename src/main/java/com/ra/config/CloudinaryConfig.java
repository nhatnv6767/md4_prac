package com.ra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    
    private String apiKey;
}
