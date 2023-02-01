package com.linklegel.apiContact.Services.Traffic;

import com.google.gson.JsonElement;
import com.linklegel.apiContact.Configuration.ConfigurationFiles.ApiContactProperties;
import com.linklegel.apiContact.Entities.Dto.request.DynamicRequestApiBody;
import com.linklegel.apiContact.ProjectUtils.ProjectUtils;
import com.linklegel.apiContact.Services.RequestSenders.HttpClientService.HttpClientService;
import com.linklegel.apiContact.Services.RequestSenders.RetrofitService.ICurrencyRequest;
import com.linklegel.apiContact.Services.RequestSenders.RetrofitService.RetrofitExecuter;
import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TrafficService implements ITraffic {
    private final ICurrencyRequest outSourceRequest;
    private final HttpClientService httpClientService;

    private final ApplicationContext applicationContext;

    public TrafficService(ICurrencyRequest outSourceRequest, HttpClientService httpClientService, ApiContactProperties apiContactProperties, ApplicationContext applicationContext) {
        this.outSourceRequest = outSourceRequest;
        this.httpClientService = httpClientService;
        this.applicationContext = applicationContext;
    }


    @Override
    public ResponseEntity<?> sendGetRequestWithParameters(DynamicRequestApiBody dynamicRequestApiBody) throws Exception {
        String finalUrl = ProjectUtils.combineUrl(dynamicRequestApiBody);
        ResponseEntity<?> finalResponse = null;
        if (checkSuitForRetrofit(finalUrl)) {
            sendRequestFinal(ProjectUtils.SENDER_TYPE_RETROFIT, ProjectUtils.METHOD_TYPE_GET, dynamicRequestApiBody, null);
        } else {
            finalResponse = sendRequestFinal(ProjectUtils.SENDER_TYPE_HTTP_CLIENT, ProjectUtils.METHOD_TYPE_GET, dynamicRequestApiBody, null);
        }

        return finalResponse;
    }

    @Override
    public ResponseEntity<?> sendPostRequestWithJsonBody(DynamicRequestApiBody dynamicRequestApiBody, JSONObject jsonObject) throws Exception {

        String finalUrl = ProjectUtils.combineUrl(dynamicRequestApiBody);
        ResponseEntity<?> finalResponse = null;
        if (checkSuitForRetrofit(finalUrl)) {
            sendRequestFinal(ProjectUtils.SENDER_TYPE_RETROFIT, ProjectUtils.METHOD_TYPE_POST, dynamicRequestApiBody, jsonObject);
        } else {
            finalResponse = sendRequestFinal(ProjectUtils.SENDER_TYPE_HTTP_CLIENT, ProjectUtils.METHOD_TYPE_POST, dynamicRequestApiBody, jsonObject);
        }
        return finalResponse;

    }


    private boolean checkSuitForRetrofit(String url) {
        /**
         * if return true suit for Retrofit 2.0
         * if return false not-suitable for Retrofit 2.0
         * */
/*        String urlString = url.toString();
        Pattern p = Pattern.compile("[^a-z0-9 : /]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(urlString);
        boolean b = !m.find();
        return b;*/ // todo burası retrofit kurulduğunda değişecek
        return false;
    }

    private Map<String, String> bodyParameterBuilder(@NotNull List<String> needKeys, @NotNull DynamicRequestApiBody prmBody) {
        Map<String, String> requestParametersBody = prmBody.getRequestParameters(); //Todo buradaki mantık değişmeli burası kontrol edilip değişecek

        if (needKeys.size() != requestParametersBody.size()) {
            System.err.println("Hata Oluştu." + this.getClass().getName() + " 'ı kontrol edin ");
            System.err.println("Gelen İsteğin Parametre Sayısı Fonksiyon İhtiyacına Uymuyor!  400 Hatası Çevir. ");// todo hata düzenleme eklenebilir @MetehanBulut
            return null;
        }
        Map<String, String> tmpMap = new HashMap<>();
        for (String needKeysCurrentValue : needKeys) {
            if (requestParametersBody.containsKey(needKeysCurrentValue))
                tmpMap.put(needKeysCurrentValue, requestParametersBody.get(needKeysCurrentValue));
            else {
                System.err.println("Hata Oluştu." + this.getClass().getName() + " 'ı kontrol edin ");
                System.err.println("Gelen İstek Parametreleri Fonksiyonla Uyuşmuyor ");// todo hata düzenleme eklenebilir @MetehanBulut
                return null;
            }
        }
        return tmpMap;
    }

    private ResponseEntity<?> responseAnalyzer() {
        return null;
    }

    private ResponseEntity<?> sendRequestFinal(@NotNull Integer senderType, @NotNull Integer methodType, DynamicRequestApiBody dynamicRequestApiBody, JSONObject jsonBody) throws Exception {
        List<String> letNeedKeys = new ArrayList<>();
        Map<String, String> finalRequestParams =null;
        Map<String, String> tmpRequestBody = dynamicRequestApiBody.getRequestParameters();
        if(tmpRequestBody!=null)
            for (String currentKey : tmpRequestBody.keySet()) {
            letNeedKeys.add(currentKey);
        }
        if(dynamicRequestApiBody.getRequestParameters()!=null)
            finalRequestParams=bodyParameterBuilder(letNeedKeys, dynamicRequestApiBody);

        String finalUrl = ProjectUtils.combineUrl(dynamicRequestApiBody);


        if (senderType.equals(ProjectUtils.SENDER_TYPE_HTTP_CLIENT)) {
            if (methodType.equals(ProjectUtils.METHOD_TYPE_GET)) {
                try {
                    ResponseEntity<String> tmpResponse = httpClientService.sendGet(finalUrl, finalRequestParams);
                    return new ResponseEntity<>(tmpResponse, HttpStatus.OK).getBody();
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
                }
            } else if (methodType.equals(ProjectUtils.METHOD_TYPE_POST)) {
                try {
                    ResponseEntity<?> tmpResponse = httpClientService.sendPost(finalUrl, ProjectUtils.convertJsonToMapObject(jsonBody));
                    return new ResponseEntity<>(tmpResponse, HttpStatus.OK).getBody();
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
                }

            } else {
                //todo buraya diğer method tipleri gelebilir
                return null;
            }
        }
        else if (senderType.equals(ProjectUtils.SENDER_TYPE_RETROFIT)) {
            if (methodType.equals(ProjectUtils.METHOD_TYPE_GET)) {
                JsonElement tmpResponse;
                try {
                    tmpResponse = RetrofitExecuter.executeInBlock(outSourceRequest.sendGetRequestWithParams(finalUrl, finalRequestParams));
                    return new ResponseEntity<>(tmpResponse, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
                }
            } else if (methodType.equals(ProjectUtils.METHOD_TYPE_POST)) {
                JsonElement tmpResponse;
                try {
                    tmpResponse = RetrofitExecuter.executeInBlock(outSourceRequest.sendPostRequest(finalUrl, jsonBody));
                    return new ResponseEntity<>(tmpResponse, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
                }
            } else {
                System.out.println("sdkfjslkdjflkdsjfk");
                return null;
                //todo buraya diğer method tipleri gelebilir

            }
        } else {
            throw new Exception("Hata var ");
        }
    }



}




















