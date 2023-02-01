package com.linklegel.apiContact.Entities.Dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class DynamicRequestApiBody {


    private String requestBaseHost;
    private String requestBaseUrl;
    private  String requestParamEndpointEnd;
    private  String requestParamEndpointStart;
    @Nullable
    private Map<String,String> requestParameters;

}
