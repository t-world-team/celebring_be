package com.tworld.celebring.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cloudinary {
    @Value("${cloudinary.api-key}")
    private String CLOUDINARY_API_KEY;

    @Value("${cloudinary.api-secret}")
    private String CLOUDINARY_API_SECRET;

    @Value("${cloudinary.cloud-name}")
    private String CLOUDINARY_CLOUD_NAME;


    @Bean
    public com.cloudinary.Cloudinary coudinaryConfig() {
        Map config = new HashMap();
        config.put("cloud_name", CLOUDINARY_CLOUD_NAME);
        config.put("api_key", CLOUDINARY_API_KEY);
        config.put("api_secret", CLOUDINARY_API_SECRET);
        com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(config);
        return cloudinary;
    }
}
