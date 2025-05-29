package com.musadzeyt.momentumapi.config;

import com.musadzeyt.momentumapi.security.CustomAuthenticationEntryPoint;
import com.musadzeyt.momentumapi.security.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security settings, including JWT authentication handling.
 * <p>
 * Configures stateless session management, endpoint authorization rules, exception handling,
 * and integrates custom JWT filter and authentication entry point.
 * </p>
 * <p>
 * Defines:
 * <ul>
 *   <li>A BCryptPasswordEncoder bean for password hashing.</li>
 *   <li>A SecurityFilterChain bean to customize HTTP security.</li>
 *   <li>An AuthenticationManager bean for programmatic authentication support.</li>
 * </ul>
 * </p>
 * <p>
 * Endpoints in {@link #AUTH_WHITELIST} are publicly accessible; all other requests require authentication.
 * </p>
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    /**
     * Filter that extracts and validates JWT tokens from incoming requests.
     */
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Entry point for handling authentication failures (returns 401 Unauthorized).
     */
    private final CustomAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * Public endpoints that do not require authentication.
     */
    private static final String[] AUTH_WHITELIST = {
            "/auth/login",
            "/auth/register",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    /**
     * Bean for password encoding using BCrypt algorithm.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the SecurityFilterChain to enforce:
     * <ul>
     *   <li>CSRF disabled.</li>
     *   <li>CORS with default settings.</li>
     *   <li>Public access to login/register endpoints.</li>
     *   <li>Authentication required for all other requests.</li>
     *   <li>JWT-based exception handling and stateless sessions.</li>
     *   <li>Inclusion of the {@link JwtRequestFilter} before the username/password filter.</li>
     * </ul>
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Register JWT filter before the standard authentication filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the AuthenticationManager bean, allowing injection for authentication operations.
     *
     * @param authenticationConfiguration the configuration to build the AuthenticationManager
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
