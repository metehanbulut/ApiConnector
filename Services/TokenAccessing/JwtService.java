package com.linklegel.apiContact.Services.TokenAccessing;

import com.linklegel.apiContact.Configuration.ConfigurationFiles.JsonWebTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    JsonWebTokenProperties jsonWebTokenProperties;
 /**
  * Current_Resource:_18_01_2023
  * https://www.youtube.com/watch?v=gcid-zj7J7U
  * */


    public String findUsername(String token)
    {
        return exportToken(token, Claims::getSubject);
    }
    private <T> T exportToken(String token, Function<Claims,T> claimsTFunction) {
        final Claims claims= Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
        return claimsTFunction.apply(claims);
    }
    private Key getKey()
    {
        byte[] key = Decoders.BASE64.decode(jsonWebTokenProperties.getSecretKey());
        return Keys.hmacShaKeyFor(key);
    }

    public boolean tokenControl(String jwtToken, UserDetails userDetails)
    {
        final String userName= findUsername(jwtToken);
        return(userName.equals(userDetails.getUsername()) && // tokendaki username ile var olan username Aynı mı var mı ?
                !exportToken(jwtToken,Claims::getExpiration).before(new Date()) // token Expire olmuş mu ?
        );
    }

    //yeni Jwt Token Üretme
    public String generateToken(UserDetails user)
    {
        return Jwts.builder().setClaims((new HashMap<>()))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }
}
