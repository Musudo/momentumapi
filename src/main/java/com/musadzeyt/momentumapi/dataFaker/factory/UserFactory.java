package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a User with fake data.
     *
     * @return a new User instance with default values.
     */
    public User create() {
        return User.builder()
                .firstName("Guest")
                .lastName("User")
                .email("guest@email.com")
                .roles(Set.of("ROLE_USER"))
                .password(passwordEncoder.encode("1Password"))
                .build();
    }
}
