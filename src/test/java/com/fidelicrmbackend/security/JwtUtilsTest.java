package com.fidelicrmbackend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JwtUtils component.
 * <p>
 * Follows TDD Red-Green-Refactor cycle.
 * Verifies that tokens can be generated, valid tokens are accepted,
 * and invalid/expired tokens are rejected.
 * </p>
 *
 * @author Fidelicrm Team
 * @since 1.0.0
 */
@SpringBootTest
@TestPropertySource(properties = {
        "security.jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970",
        "security.jwt.expiration-ms=60000" // 1 minute for testing
})
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Test token generation.
     * <p>
     * Scenario:
     * Given a valid username
     * When generateTokenFromUsername is called
     * Then a non-null, non-empty JWT string should be returned.
     * </p>
     */
    @Test
    void whenGenerateToken_thenReturnsString() {
        String token = jwtUtils.generateJwtToken("testuser");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    /**
     * Test token validation.
     * <p>
     * Scenario:
     * Given a valid JWT token
     * When validateJwtToken is called
     * Then it should return true.
     * </p>
     */
    @Test
    void givenValidToken_whenValidate_thenTrue() {
        String token = jwtUtils.generateJwtToken("testuser");
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    /**
     * Test username extraction.
     * <p>
     * Scenario:
     * Given a valid JWT token for "testuser"
     * When getUserNameFromJwtToken is called
     * Then it should return "testuser".
     * </p>
     */
    @Test
    void givenValidToken_whenExtractUsername_thenReturnsUsername() {
        String token = jwtUtils.generateJwtToken("testuser");
        assertEquals("testuser", jwtUtils.getUserNameFromJwtToken(token));
    }

    @Test
    void whenInvalidSignature_thenReturnsFalse() {
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject("testuser")
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor("DifferentSecretKeyForTestingSignature1234567890"
                        .getBytes(java.nio.charset.StandardCharsets.UTF_8)))
                .compact();
        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void whenMalformedToken_thenReturnsFalse() {
        assertFalse(jwtUtils.validateJwtToken("malformed.token"));
    }

    @Test
    void whenExpiredToken_thenReturnsFalse() {
        // Create an expired token manually using the same secret
        String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new java.util.Date(System.currentTimeMillis() - 10000))
                .setExpiration(new java.util.Date(System.currentTimeMillis() - 1000)) // Expired 1 second ago
                .signWith(io.jsonwebtoken.security.Keys
                        .hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8)))
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void whenEmptyToken_thenReturnsFalse() {
        assertFalse(jwtUtils.validateJwtToken(""));
        assertFalse(jwtUtils.validateJwtToken(null));
    }
}
