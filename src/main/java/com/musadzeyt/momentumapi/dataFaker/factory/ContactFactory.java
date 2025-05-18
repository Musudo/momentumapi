package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.repository.IInstitutionRepository;
import com.musadzeyt.momentumapi.repository.IAppUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ContactFactory {
    private final Faker faker;
    private final IAppUserRepository userRepository;
    private final IInstitutionRepository institutionRepository;

    /**
     * Creates a Contact with fake data.
     *
     * @return a new Contact instance with default values.
     */
    public Contact create() {
        return Contact.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .jobTitle(faker.options().option("Director", "Coordinator", "Mentor", "Analyst", "Developer"))
                .email1(faker.internet().emailAddress())
                .phone1(faker.phoneNumber().phoneNumber())
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .institution(faker.options().option(institutionRepository.findAll()).getFirst())
                .createdAt(
                        LocalDateTime.parse(faker.timeAndDate().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }
}
