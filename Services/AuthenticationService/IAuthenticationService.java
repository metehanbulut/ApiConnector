package com.linklegel.apiContact.Services.AuthenticationService;

import com.linklegel.apiContact.Entities.Auth.User;
import com.linklegel.apiContact.Entities.Dto.request.LoginRequest;
import com.linklegel.apiContact.Entities.Dto.request.SignupRequest;
import com.linklegel.apiContact.Entities.Dto.response.JwtTokenStringResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
  JwtTokenStringResponse signInAndReturnJWT(LoginRequest loginRequest);

  ResponseEntity<?> signUpNoReturn(SignupRequest signupRequest);
}

