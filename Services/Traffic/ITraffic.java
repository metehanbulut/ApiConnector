package com.linklegel.apiContact.Services.Traffic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.linklegel.apiContact.Entities.Dto.request.DynamicRequestApiBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

public interface ITraffic {


     ResponseEntity<?> sendGetRequestWithParameters(DynamicRequestApiBody parameters) throws Exception;

     ResponseEntity<?> sendPostRequestWithJsonBody(DynamicRequestApiBody parameters, JSONObject jsonObject) throws Exception;

}
