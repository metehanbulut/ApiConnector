package com.linklegel.apiContact.Configuration;

import com.linklegel.apiContact.Configuration.ConfigurationFiles.ApiContactProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.JsonWebTokenProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.RetrofitProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControlConfigurations {

    @Autowired
    private ApiContactProperties apiContactProperties;
    @Autowired
    private JsonWebTokenProperties jsonWebTokenProperties;
    @Autowired
    private RetrofitProperties retrofitProperties;
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_RESET = "\u001B[0m";
    @PostConstruct
    public void init()
    {
        System.out.println(TEXT_YELLOW+
                "Sistem Sabitleri ->"+"\n"+"\n"+
                "apiContactConnected"+":"+apiContactProperties.isExample()+"   "+"\n"+
                "jwtExpirationMs"+":"+jsonWebTokenProperties.getExpirationMs()+"   "+"\n"+
                "jwtSecretKey"+":"+jsonWebTokenProperties.getSecretKey()+"   "+"\n"+
                "gatewayBaseUrl"+":"+apiContactProperties.getGatewayBaseUrl()+"   "+"\n"+
                "outSourceUrl"+":"+apiContactProperties.getCurrencyBaseUrl()+"   "+"\n"+
                "retrofitTimeOut"+":"+retrofitProperties.getTimeOut()+"   "+"\n"+TEXT_RESET
        );
    }
}
