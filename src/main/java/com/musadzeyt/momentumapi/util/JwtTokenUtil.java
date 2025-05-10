package com.musadzeyt.momentumapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility component for creating and validating JSON Web Tokens (JWT) using the HS512 algorithm.
 * <p>
 * <ul>
 *   <li>Generates JWT tokens with configurable secret and expiration time.</li>
 *   <li>Retrieves claims such as subject (username) and expiration.</li>
 *   <li>Validates tokens against user details and expiration.</li>
 * </ul>
 * <p>
 * Configuration properties (in application properties/yaml):
 * <ul>
 *   <li><code>jwt.secret</code> – Base64-encoded secret key for signing tokens.</li>
 *   <li><code>jwt.expirationTime</code> – Token validity duration in milliseconds.</li>
 * </ul>
 * <p>
 * Usage example:
 * <pre>{@code
 * String token = jwtTokenUtil.generateToken(userDetails);
 * boolean valid = jwtTokenUtil.validateToken(token, userDetails);
 * String username = jwtTokenUtil.getUsernameFromToken(token);
 * }</pre>
 *
 * @see <a href="https://github.com/jwtk/jjwt">io.jsonwebtoken (JJWT) Documentation</a>
 */
@Component
public class JwtTokenUtil {

    /**
     * Base64-encoded secret key for signing and verifying JWT tokens.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Expiration time for generated tokens, in milliseconds.
     */
    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token string
     * @return the subject (username) claim
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token string
     * @return the expiration {@link Date} claim
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves a specific claim from the token using the provided resolver function.
     *
     * @param <T>            the type of the claim
     * @param token          the JWT token string
     * @param claimsResolver a function to extract a claim from {@link Claims}
     * @return the extracted claim value
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT token and retrieves all claims using the configured secret key.
     *
     * @param token the JWT token string
     * @return the parsed {@link Claims} body
     */
    private Claims getAllClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks whether the provided token has expired.
     *
     * @param token the JWT token string
     * @return {@code true} if the token is expired; {@code false} otherwise
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Creates a new JWT token for the given user details, using the configured secret and expiration.
     *
     * @param userDetails the authenticated user details
     * @return the generated JWT token string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Builds a JWT token with given claims and subject, sets issued and expiration dates,
     * and signs it using HS512 and the configured secret key.
     *
     * @param claims  additional claims to include in the token
     * @param subject the subject (usually username) of the token
     * @return the signed JWT token string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validates the given token against the user details and checks expiration.
     *
     * @param token       the JWT token string
     * @param userDetails the user details to validate against
     * @return {@code true} if the token is valid and not expired; {@code false} otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
