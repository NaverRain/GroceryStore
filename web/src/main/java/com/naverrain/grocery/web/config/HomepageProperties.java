package com.naverrain.grocery.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.homepage")
public class HomepageProperties {

    private int categoriesLimit = 12;

    private int productsLimit = 9;
}
