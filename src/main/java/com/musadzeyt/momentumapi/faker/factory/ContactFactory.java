package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.repository.IInstitutionRepository;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContactFactory {
    private final Faker faker;
    private final IUserRepository userRepository;
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
                .jobTitle(faker.job().title())
                .email1(faker.internet().emailAddress())
                .phone1(faker.phoneNumber().phoneNumber())
                .user(userRepository.findByEmail("musa@email.com").orElse(null))
                .institution(institutionRepository.findAll().getFirst())
                .build();
    }

    /**
     * Optional: Customize the contact after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Contact initialize(Contact contact) {
        // Perform any post-processing if needed
        return contact;
    }
}
