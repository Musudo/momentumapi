package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalParticipantFactory {
    private final Faker faker;

    /**
     * Creates a ExternalParticipant with fake data.
     *
     * @return a new ExternalParticipant instance with default values.
     */
    public ExternalParticipant create() {
        return ExternalParticipant.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();
    }
}
