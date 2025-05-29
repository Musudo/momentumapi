package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.enums.Role;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppUserFactory {
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a User with fake data.
     *
     * @return a new User instance with default values.
     */
    public AppUser create() {
        return AppUser.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .roles(Set.of(Role.ROLE_USER))
                .password(passwordEncoder.encode("1Password"))
                .build();
    }

    /**
     * Creates a User with fake data.
     *
     * @return a new User instance with default values.
     */
    public AppUser createTestUser() {
        return AppUser.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@email.com")
                .roles(Set.of(Role.ROLE_USER))
                .password(passwordEncoder.encode("1Password"))
                .build();
    }
}
