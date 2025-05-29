package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service implementing Spring Security's {@link UserDetailsService} to load user-specific data
 * during authentication and to expose utility methods for accessing the currently authenticated user.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Lookup a {@link AppUser} by email and convert it to a Spring Security {@link UserDetails} object.</li>
 *   <li>Provide helper methods to retrieve the current username and {@link AppUser} entity
 *       from the security context.</li>
 * </ul>
 * <p>
 * Usage in security configuration:
 * <pre>{@code
 * auth.userDetailsService(customUserDetailsService)
 *     .passwordEncoder(passwordEncoder());
 * }</pre>
 * <p>
 * Domain lookup exceptions are wrapped as Spring Security's {@link UsernameNotFoundException} or
 * custom {@link EntityNotFoundException} to signal authentication failures or missing entities.
 *
 * @see UserDetailsService
 * @see SecurityContextHolder
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository for accessing {@link AppUser} entities by email.
     */
    private final AppUserRepository userRepository;

    /**
     * Loads the user details by email (used as username in authentication).
     * <p>
     *
     * @param username the email of the user to load
     * @return a Spring Security {@link org.springframework.security.core.userdetails.User}
     * populated with email, hashed password, and authorities
     * @throws UsernameNotFoundException if no user is found for the given email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), // Must be the encoded (hashed) password
                new ArrayList<>()     // Authorities (roles) can be added here
        );
    }

    /**
     * Retrieves the username (email) of the currently authenticated user.
     *
     * @return the username if authenticated; {@code null} otherwise
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }

    /**
     * Retrieves the {@link AppUser} entity of the currently authenticated user.
     * <p>
     * Uses {@link #getCurrentUsername()} to lookup the email and fetches the
     * corresponding {@link AppUser} from the repository.
     *
     * @return the {@link AppUser} entity if found; {@code null} if no user is authenticated
     * @throws EntityNotFoundException if no {@link AppUser} is found for the current username
     */
    public AppUser getCurrentUser() {
        String username = getCurrentUsername();
        if (username != null) {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
        }
        return null;
    }
}
