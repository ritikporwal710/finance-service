package com.financetrack.development.security;

import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;


    public String verifyTokenAndGetUserById(String token){
        try {
            System.out.println("token inside jwtutils" +  token);

            Claims claims = Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(token)
            .getBody();
    
            
    
            return claims.get("userId", String.class);
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            return "Error in JWT Utils";
        }
       
    }


    public String generateToken(UUID userId) {
        System.out.println("userId: in jwtutils " + userId);
        return Jwts.builder()
                .claim("userId", userId.toString())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // Pass raw secret string
                .compact();
    }
}
