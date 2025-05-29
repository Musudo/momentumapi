package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.dataFaker.factory.AppUserFactory;
import com.musadzeyt.momentumapi.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class AppUserGenerator {
    private final AppUserFactory userFactory;
    private final AppUserRepository userRepository;

    /**
     * Creates a list of users using the UserFactory.
     *
     * @param count the number of users to generate.
     * @return a List of generated User objects.
     */
    public List<AppUser> createUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> userFactory.create())
                .collect(Collectors.toList());
    }

    /**
     * Creates a single user using the UserFactory.
     *
     * @return a User object.
     */
    public AppUser createTestUser() {
        return userRepository.save(userFactory.createTestUser());
    }
}
