package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class VoiceMemoFactory {
    private final Faker faker;
    private final IUserRepository userRepository;

    /**
     * Creates a VoiceMemo with fake data.
     *
     * @return a new VoiceMemo instance with default values.
     */
    public VoiceMemo create() {
        return VoiceMemo.builder()
                .path("/test/path")
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .createdAt(
                        LocalDateTime.parse(faker.date().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }

    /**
     * Optional: Customize the voiceMemo after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public VoiceMemo initialize(VoiceMemo voiceMemo) {
        // Perform any post-processing if needed
        return voiceMemo;
    }
}
