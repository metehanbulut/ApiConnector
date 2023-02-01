package com.linklegel.apiContact.Services.AuthenticationService;

import com.linklegel.apiContact.Entities.Auth.User;
import com.linklegel.apiContact.Entities.Dao.UserRepository;
import com.linklegel.apiContact.Entities.Dto.request.LoginRequest;
import com.linklegel.apiContact.Entities.Dto.request.SignupRequest;
import com.linklegel.apiContact.Entities.Dto.response.JwtResponse;
import com.linklegel.apiContact.Entities.Dto.response.JwtTokenStringResponse;
import com.linklegel.apiContact.Entities.Dto.response.MessageResponse;
import com.linklegel.apiContact.Security.JWT.JwtUtils;
import com.linklegel.apiContact.Security.Services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticationService implements IAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public JwtTokenStringResponse signInAndReturnJWT(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        /**
         * Rol yönetimi kullanılırsa buradaki panelin Açılması gerekiyor ! @author=metehanBulut
         * */
    /*List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());*/
        JwtTokenStringResponse tokenString =new JwtTokenStringResponse(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()).getAccessToken());
        return tokenString;
    }

    @Transactional
    @Override
    public ResponseEntity<?>  signUpNoReturn(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        try{
            User user = User.builder().username(signupRequest.getUsername()).email(signupRequest.getEmail()).password(encoder.encode(signupRequest.getPassword())).build();
            userRepository.save(user);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
   /*
    Set<String> strRoles = signUpRequest.getRole();
   Set<Role> roles = new HashSet<Role>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);*/
        return null;
    }
}
