package com.crm.helper;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.crm.model.RefreshToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

//method to generating token
//validate
//is expiry
//util

@Component
public class JwtHelperUtil {

    private static String SECRET_KEY = "secret";

    public static final long JWT_TOKEN_VALIDITY =30 * 60 * 1000;
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
  
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken( userDetails.getUsername(),claims);
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

	}
	/*
	 * public String generateTokenFromUsername(String username) { return
	 * Jwts.builder().setSubject(username).setIssuedAt(new Date())
	 * .setExpiration(new Date((new Date()).getTime() +
	 * JWT_TOKEN_VALIDITY)).signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	 * .compact(); }
	 */
    
    
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

		/*
		 * refreshToken.setUser(userRepository.findById(userId).get());
		 * refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		 */
        refreshToken.setToken(UUID.randomUUID().toString());

		/*
		 * refreshToken = refreshTokenRepository.save(refreshToken);
		 */        return refreshToken;
      }
//    private String createToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
//    }
    
    public String createToken(String subject, Map<String, Object> claims) {
        try {
            // Ensure claims and subject are not null
            if (claims == null || subject == null) {
                throw new IllegalArgumentException("Claims or Subject cannot be null");
            }

            // Create the JWT token
            return Jwts.builder()
                .setClaims(claims)  // Set the claims (optional)
                .setSubject(subject) // Set the subject (e.g., username)
                .setIssuedAt(new Date()) // Set the issue date to now
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Set the expiration time
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // Use Base64 for encoding
                .compact(); // Return the compacted JWT token
        } catch (Exception e) {
            // Log the exception and throw it or handle it
            System.out.println("Error creating JWT token: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating JWT token", e);  // Rethrow or handle accordingly
        }
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	
	
}
