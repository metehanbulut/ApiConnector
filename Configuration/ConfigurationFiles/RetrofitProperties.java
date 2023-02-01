package com.linklegel.apiContact.Configuration.ConfigurationFiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "retrofit")
public class RetrofitProperties {
    private Integer timeOut=30;
}
