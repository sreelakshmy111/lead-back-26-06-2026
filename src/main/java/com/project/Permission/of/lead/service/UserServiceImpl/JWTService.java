package com.project.Permission.of.lead.service.UserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import javax.crypto.KeyGenerator;


@Service
public class JWTService {

    private String secretkey = "";

    KeyGenerator keyGen;
    {
      try {
        // Generate HMAC-SHA256 key
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGen.generateKey();
        secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());

        System.out.println("Generated secret key: " + secretkey);
    } catch (NoSuchAlgorithmException e) {

        throw new RuntimeException(e);
    }
}


public String generateToken(String username, List<String> role) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);


        return Jwts.builder()
                .claims(claims) // ✅ directly set claims map
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getkey()) // ✅ your secret key
                .compact();
    }


    public SecretKey getkey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }




    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }


   public boolean validateToken(String token,UserDetails userDetails) {
        final String username=extractUserName(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
