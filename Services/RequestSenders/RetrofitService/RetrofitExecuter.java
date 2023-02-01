package com.linklegel.apiContact.Services.RequestSenders.RetrofitService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
@Service
public class RetrofitExecuter {
    public static <T> T executeInBlock(Call<T> request) {
        try {
            Response<T> response = request.execute();

            if (!response.isSuccessful()) {
                log.error("Request completed unsuccessfully with statusCode:{} and reason:{}",
                        response.code(), response.errorBody().toString());
                System.err.println("Request completed unsuccessfully with statusCode:{} and reason:{}"+"     "+
                        response.code()+"    "+ response.errorBody().toString());
                System.err.println(request.request().toString());
                throw new HttpException(response);
            }
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
