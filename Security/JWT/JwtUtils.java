package com.linklegel.apiContact.Security.JWT;

import com.linklegel.apiContact.Configuration.ConfigurationFiles.JsonWebTokenProperties;
import com.linklegel.apiContact.Security.Services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    /**
     * https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication/tree/master/src/main
     * * ****/
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired private JsonWebTokenProperties jsonWebTokenProperties;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jsonWebTokenProperties.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS256, jsonWebTokenProperties.getSecretKey())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jsonWebTokenProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jsonWebTokenProperties.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
