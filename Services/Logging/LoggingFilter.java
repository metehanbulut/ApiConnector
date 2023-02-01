package com.linklegel.apiContact.Services.Logging;

import com.linklegel.apiContact.Entities.Dao.IRequestLogDao;
import com.linklegel.apiContact.Entities.Logging.RequestLog;
import com.linklegel.apiContact.ProjectUtils.ProjectUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class LoggingFilter {

    /**
     *
     * SLF4J kütüphesi kullanıldı.
     * Anotasyondan yararlanılmadı. Kütüphane İmport edildi.
     *
     * Hazır kod kullanıldı.
     * https://www.youtube.com/watch?v=s841xbEPg4I&t=931s
     *
     * @Author= mbulut
     *
     * **/
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    private final IRequestLogDao requestLogDao;

    public LoggingFilter(IRequestLogDao requestLogDao) {
        this.requestLogDao = requestLogDao;
    }



    public void generateLogOfRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();
/*
        filterChain.doFilter(contentCachingRequestWrapper,contentCachingResponseWrapper);

        already working in AuthTokenFilter.java
*/

        long timeTaken = System.currentTimeMillis()- startTime;
        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
        String senderIpAdress= request.getRemoteAddr().toString();
        saveLogToDatabase(request.getMethod(),request.getRequestURL().toString(),
                requestBody,String.valueOf(response.getStatus()),responseBody,timeTaken,senderIpAdress);
        LOGGER.info("filter Logs: METHOD={}; REQUESTURI={}; REQUESTBODY= {}; RESPONSECODE ={}; RESPONSEBODY={} TIMETAKEN={}",request.getMethod(),request.getRequestURL().toString(),
        requestBody,response.getStatus(),responseBody,timeTaken+"ms");
        contentCachingResponseWrapper.copyBodyToResponse();

    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray,0, contentAsByteArray.length,characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Boolean saveLogToDatabase(String method, String requestUrl, String requestBody, String responseCode, String responseBody, Long timeTaken, String ipAddress) {
        RequestLog requestTemp = new RequestLog();
        String tmpReq="";
        if(requestBody.isEmpty() || requestBody.equals(""))
        {
            tmpReq="";
        }
        else
        {
            tmpReq=ProjectUtils.convertJsonStringToJsonObject(requestBody).toJSONString();
        }

        requestTemp.setMethodType(method);
        requestTemp.setUrl(requestUrl);
        requestTemp.setRequestBody(tmpReq);
        requestTemp.setResponseCode(responseCode);
        requestTemp.setResponseBody(responseBody);
        requestTemp.setElapsedTime(timeTaken);
        requestTemp.setIpAdress(ipAddress);
        try{
            requestLogDao.save(requestTemp);
            return true;
        }
        catch(Exception e )
        {
            System.err.println("Bir Hata Oluştu Atılan İsteğin Log Kaydı Oluşturulamadı");
            return false;
        }

    }



}
