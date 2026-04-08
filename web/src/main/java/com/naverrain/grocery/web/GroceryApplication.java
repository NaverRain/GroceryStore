package com.naverrain.grocery.web;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import com.naverrain.grocery.web.config.HomepageProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.naverrain.grocery")
@EnableConfigurationProperties(HomepageProperties.class)
public class GroceryApplication {
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("GOOGLE_OAUTH_CLIENT_ID", dotenv.get("GOOGLE_OAUTH_CLIENT_ID"));
		System.setProperty("GOOGLE_OAUTH_CLIENT_SECRET", dotenv.get("GOOGLE_OAUTH_CLIENT_SECRET"));

		System.setProperty("FACEBOOK_OAUTH_CLIENT_ID", dotenv.get("FACEBOOK_OAUTH_CLIENT_ID"));
		System.setProperty("FACEBOOK_OAUTH_CLIENT_SECRET", dotenv.get("FACEBOOK_OAUTH_CLIENT_SECRET"));

		System.setProperty("AZURE_OAUTH_CLIENT_ID", dotenv.get("AZURE_OAUTH_CLIENT_ID"));
		System.setProperty("AZURE_OAUTH_CLIENT_SECRET", dotenv.get("AZURE_OAUTH_CLIENT_SECRET"));

		SpringApplication.run(GroceryApplication.class, args);
	}

}
