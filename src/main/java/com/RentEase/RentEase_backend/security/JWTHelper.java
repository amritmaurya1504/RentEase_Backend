package com.RentEase.RentEase_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTHelper {

    // generate token for user
    public String generateToken(UserDetails userDetails){
       return Jwts.builder().setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() +
                       1000 * 60 * 24))
               .signWith(getSignInKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    private Key getSignInKey(){
        byte[] key = Decoders.BASE64.decode("W5IK56UXY45CLNPLSYYDQGEWPCRSCEOEM17I52CMQM2OGG45AQ");
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpires(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && isTokenExpires(token));
    }


}
