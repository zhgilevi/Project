package com.example.demo.util;

import com.example.demo.service.AppUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

  @Value("${messanger.app.jwtSecret}")
  private String jwtSecret;

  @Value("${messanger.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication){
    AppUserDetailService userPrincipal = (AppUserDetailService) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date((new Date(System.currentTimeMillis()).getTime()+jwtExpirationMs)))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String authToken) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken){


    try{
    Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
    return true;

    }
    catch (SignatureException e) {
      logger.log(Level.SEVERE, "Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.log(Level.SEVERE, "Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.log(Level.SEVERE, "JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.log(Level.SEVERE, "JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, "JWT claims string is empty: {}}", e.getMessage());
    }

    return false;
  }

}
