package com.fidelicrmbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * <p>
 * Responsible for:
 * 1. Generating tokens from Username.
 * 2. Validating tokens (Signature, Expiration).
 * 3. Extracting claims (Username) from tokens.
 * </p>
 *
 * @author Hugo Bardales
 * @since 1.0.0
 */

@Component
public class JwtUtils {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-ms}")
    private int jwtExpirationMs;

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username to encode in the token.
     * @return A signed JWT string.
     */
    public String generateJwtToken(String username) {
        return Jwts.builder() // punto de inicio del constructor
                .setSubject(username) // store of username para que cuando el token regrese al servidor sepa exactamente quien es
                .setIssuedAt(new Date()) // control of time
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)) // Calcula la fecha de caducidad
                .signWith(key(), SignatureAlgorithm.HS512) // Genera un JWT válido para 'n' horas
                .compact(); // Coding en Base64 resultando Header.Payload.Signature: yJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJodWdv...
    }

    /**
     * Validates a JWT token.
     *
     * @param authToken The token to validate.
     * @return true if valid, false if expired, malformed, or invalid signature.
     */
    public boolean validateJwtToken(String authToken) { // Detector
        try {
            // Comprueba Integridad coincidan A.B con C, Sintaxis (AAA.BBB.CCC) y la fecha de expiración del token
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // In a real app we would log the error here
            return false;
        }
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The token to parse.
     * @return The username contained in the subject claim.
     */
    public String getUserNameFromJwtToken(String token) { // Extractor de identidad
        return Jwts.parserBuilder().setSigningKey(key()).build() // Configuramos al lector parse con la key
                //Abre la estructura de tres partes (Header, Payload, Signature) y verifica que todo esté en orden.
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

}
