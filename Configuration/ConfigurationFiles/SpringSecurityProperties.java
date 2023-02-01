package com.linklegel.apiContact.Configuration.ConfigurationFiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "springsecurity")
public class SpringSecurityProperties {
    private String username="asd";
    private String password="asd";
}
