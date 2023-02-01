package com.linklegel.apiContact.Services.RequestSenders.RetrofitService;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface IGatewayRequests {
    @GET
    Call<JsonElement> sendGetRequest(@Url String Url);
    @GET
    Call<JsonElement> sendGetRequestWithParams(@Url String Url, @QueryMap Map<String,String> params);
    @POST
    Call<JsonElement> sendPostRequest(@Url String Url,@Body JsonElement jsonElement);

}
