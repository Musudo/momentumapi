package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppUserFactory {
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a User with fake data.
     *
     * @return a new User instance with default values.
     */
    public AppUser create() {
        return AppUser.builder()
                .firstName("Guest")
                .lastName("User")
                .email("guest@email.com")
                .roles(Set.of(RoleEnum.ROLE_USER))
                .password(passwordEncoder.encode("1Password"))
                .build();
    }
}
