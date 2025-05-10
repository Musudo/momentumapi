package com.musadzeyt.momentumapi.security;

import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter that intercepts HTTP requests to extract and validate JWT tokens.
 * <p>
 * For each incoming request, this filter:
 * <ol>
 *   <li>Retrieves the Authorization header.</li>
 *   <li>Extracts the JWT token if the header starts with "Bearer ".</li>
 *   <li>Parses the token to obtain the username.</li>
 *   <li>Validates the token and sets the authentication in the SecurityContext.</li>
 * </ol>
 * <p>
 * Exceptions thrown during parsing or validation (e.g., expired token) will propagate
 * and can be handled by an exception handler in the security configuration.
 * <p>
 * This filter executes once per request and should be registered before the
 * Spring Security authentication mechanisms.
 *
 * @see OncePerRequestFilter
 * @see JwtTokenUtil
 * @see CustomUserDetailsService
 */
@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Service to load user-specific data (e.g., authorities) from username.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Utility class for JWT token operations (parsing, validation).
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Filters each request to check for a valid JWT in the Authorization header.
     * <p>
     * If a valid token is found and the user is not already authenticated,
     * this method sets the authentication in the SecurityContextHolder.
     * <p>
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Retrieve the Authorization header
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // Check if header contains a Bearer token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                // Extract username from token
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                // Token has expired
                throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue filter chain
        chain.doFilter(request, response);
    }
}
