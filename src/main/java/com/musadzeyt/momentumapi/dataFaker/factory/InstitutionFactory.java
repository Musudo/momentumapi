package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.Institution;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class InstitutionFactory {
    private final Faker faker = new Faker();

    /**
     * Creates an Institution with fake data.
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
                .postbox("12")
                .createdAt(
                        LocalDateTime.parse(faker.timeAndDate().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }
}
