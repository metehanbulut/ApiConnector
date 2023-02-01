package com.linklegel.apiContact.Services.RequestSenders.RetrofitService;

import com.google.gson.Gson;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.ApiContactProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.RetrofitProperties;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.SpringSecurityProperties;
import com.linklegel.apiContact.ProjectUtils.ProjectUtils;
import com.linklegel.apiContact.Services.AuthenticationService.IAuthenticationService;
import com.linklegel.apiContact.Services.TokenAccessing.TokenInterceptor;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofitConfiguration {
    private final RetrofitProperties retrofitProperties;
    private final SpringSecurityProperties springSecurityProperties;
    private final IAuthenticationService authenticationService;

    private final ApiContactProperties apiContactProperties;

    public RetrofitConfiguration(RetrofitProperties retrofitProperties, SpringSecurityProperties springSecurityProperties, IAuthenticationService authenticationService, ApiContactProperties apiContactProperties) {
        this.retrofitProperties = retrofitProperties;
        this.springSecurityProperties = springSecurityProperties;
        this.authenticationService = authenticationService;
        this.apiContactProperties = apiContactProperties;
    }

    @Bean
    public OkHttpClient secureKeyClient() {
        return createDefaultClientBuilder()
                .addInterceptor(interceptor -> interceptor.proceed(interceptor.request().newBuilder()
                        .header(ProjectUtils.AUTHORIZATION_KEYWORD, Credentials.basic(springSecurityProperties.getUsername(), springSecurityProperties.getPassword()))
                        .build()))
                .build();
    }

    @Bean
    public Retrofit.Builder secureKeyBuilder(OkHttpClient secureKeyClient, Gson gson) {
        return new Retrofit.Builder()
                .client(secureKeyClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    private OkHttpClient.Builder createDefaultClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(retrofitProperties.getTimeOut(), TimeUnit.SECONDS)
                .readTimeout(retrofitProperties.getTimeOut(), TimeUnit.SECONDS)
                .writeTimeout(retrofitProperties.getTimeOut(), TimeUnit.SECONDS);
    }

    @Bean
    public IGatewayRequests gatewayRequests(Retrofit.Builder secureKeyBuilder) {
        TokenInterceptor interceptor = new TokenInterceptor(authenticationService);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return secureKeyBuilder
                .client(client)
                .baseUrl(apiContactProperties.getGatewayBaseUrl())
                .build()
                .create(IGatewayRequests.class);

    }
/*new URL(ProjectUtils.CURRENCY_API_1_BASE_URL)*/
    @Bean
    public ICurrencyRequest currencyRequest(Retrofit.Builder secureKeyBuilder) throws MalformedURLException {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        return secureKeyBuilder
                .client(client)
                .baseUrl(apiContactProperties.getCurrencyBaseUrl())
                .build()
                .create(ICurrencyRequest.class);
    }
}