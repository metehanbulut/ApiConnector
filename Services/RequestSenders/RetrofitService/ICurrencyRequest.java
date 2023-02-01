package com.linklegel.apiContact.Services.RequestSenders.RetrofitService;

import com.google.gson.JsonElement;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ICurrencyRequest {
    @GET
    Call<JsonElement> sendGetRequest(@Url String url);
    @GET
    Call<JsonElement> sendGetRequestWithParams(@Url String Url, @QueryMap Map<String,String> params);
    @POST
    Call<JsonElement> sendPostRequest(@Url String Url,@Body JSONObject jsonElement);

    @GET("https://www.tcmb.gov.tr/kurlar/today.xml")
    Call<JsonElement> getCurrencyData();
}
