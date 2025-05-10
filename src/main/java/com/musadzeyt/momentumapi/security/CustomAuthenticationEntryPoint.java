package com.musadzeyt.momentumapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Entry point for unauthorized access attempts in the Spring Security filter chain.
 * <p>
 * This component intercepts requests that fail authentication and sends a
 * 401 Unauthorized HTTP response with a standard error message.
 * <p>
 * Register this entry point in your security configuration to handle
 * authentication failures consistently across all endpoints.
 *
 * <pre>{@code
 * http
 *   .exceptionHandling()
 *     .authenticationEntryPoint(customAuthenticationEntryPoint)
 *     ...;
 * }</pre>
 *
 * @see org.springframework.security.web.AuthenticationEntryPoint
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Called when an unauthenticated user tries to access a secured resource.
     * <p>
     * Sends a 401 Unauthorized response with the message "Unauthorized".
     *
     * @param request       the incoming HTTP request
     * @param response      the HTTP response to be sent
     * @param authException the exception that caused the invocation
     * @throws IOException if an input or output error occurs while sending the error response
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
