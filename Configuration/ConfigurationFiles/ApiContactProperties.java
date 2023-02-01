package com.linklegel.apiContact.Configuration.ConfigurationFiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "apicontact")
public class ApiContactProperties { // todo buradaki configuration ayarları yapılacak hatırlanmazsa app configteki todo ya bakılabilir @MetehanBulut
    private boolean example=false;
    private String gatewayBaseUrl;
    private String currencyBaseUrl;
}




