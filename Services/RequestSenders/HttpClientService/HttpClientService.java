package com.linklegel.apiContact.Services.RequestSenders.HttpClientService;

import jakarta.validation.constraints.NotNull;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpClientService {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public ResponseEntity<String> sendGet(String urlParam,Map<String,String> parameters)  {

        String paramStr="";
        if(parameters!=null)
        {
            paramStr+="?";
            for(String w : parameters.keySet())
            {
                paramStr+=w+"="+parameters.get(w)+"&";
            }
            paramStr=paramStr.substring(0,paramStr.length()-1);
        }
        System.out.println(paramStr);
        /** BURASI DİNAMİK OLARAK YERLEŞTİRİLECEK  // todo halledilecek iş var */
        String accessToken = "ZWIxMWY4ZjIyZmZlZjA5ZDk5MzMwODA5NWY0NDg3ZmFiZmNmYThlOTBkMTA5ZWQ4ZTY4MTZkODgzN2Q0M2I0Yg";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(paramStr==null?urlParam:urlParam+paramStr))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .setHeader("access_token",accessToken)
                .build();


        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(response.body(), HttpStatus.OK);
    }

    public ResponseEntity<?> sendPost(@NotNull String url, @NotNull @RequestBody Map<Object,Object> jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(jsonBody))
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    public static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}
