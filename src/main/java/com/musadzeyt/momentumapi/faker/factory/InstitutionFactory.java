package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Institution;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class InstitutionFactory {
    private final Faker faker = new Faker();

    /**
     * Creates a Institution with fake data.
     *
     * @return a new Institution instance with default values.
     */
    public Institution create() {
        return Institution.builder()
                .name(faker.company().name())
                .countryCode(faker.address().countryCode())
                .city(faker.address().city())
                .postalCode(faker.address().postcode())
                .street(faker.address().streetName())
                .buildingNumber(faker.address().buildingNumber())
                .postbox(faker.address().mailBox())
                .build();
    }

    /**
     * Optional: Customize the institution after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Institution initialize(Institution institution) {
        // Perform any post-processing if needed
        return institution;
    }
}
