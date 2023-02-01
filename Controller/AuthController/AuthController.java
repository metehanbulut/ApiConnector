package com.linklegel.apiContact.Controller.AuthController;

import com.linklegel.apiContact.Entities.Dto.request.LoginRequest;
import com.linklegel.apiContact.Entities.Dto.request.SignupRequest;
import com.linklegel.apiContact.Entities.Dto.response.JwtTokenStringResponse;
import com.linklegel.apiContact.Entities.Dto.response.MessageResponse;
import com.linklegel.apiContact.ProjectUtils.ProjectUtils;
import com.linklegel.apiContact.Services.AuthenticationService.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
@CrossOrigin(origins = "*", maxAge = 3600)
*/
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthenticationService authenticationService;

    public AuthController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ProjectUtils.validateBindBody(result), HttpStatus.BAD_REQUEST);
        }
        JwtTokenStringResponse tokenString =authenticationService.signInAndReturnJWT(loginRequest);
        return ResponseEntity.ok(tokenString);

    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ProjectUtils.validateBindBody(result), HttpStatus.BAD_REQUEST);
        }
        authenticationService.signUpNoReturn(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/testAuth")
    public void testAuth() {
        System.out.println("Authentication No Needed");
        return;
    }

}
