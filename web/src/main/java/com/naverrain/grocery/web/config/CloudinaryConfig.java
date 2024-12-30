package com.naverrain.grocery.web.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig  {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dn6613m0p",
                "api_key", "256381459429195",
                "api_secret", "07OZlOg-SIKNI624H2vSsmgf2_w"));
    }
}
