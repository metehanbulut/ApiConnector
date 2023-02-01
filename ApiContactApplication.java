package com.linklegel.apiContact;

import com.linklegel.apiContact.Configuration.ConfigurationFiles.ApiContactProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.JsonWebTokenProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.RetrofitProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.SpringSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties(value = {ApiContactProperties.class, JsonWebTokenProperties.class, RetrofitProperties.class, SpringSecurityProperties.class})
public class ApiContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiContactApplication.class, args);
	}

}
