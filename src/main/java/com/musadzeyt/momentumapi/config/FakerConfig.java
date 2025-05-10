package com.musadzeyt.momentumapi.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that provides a shared {@link Faker} bean for generating
 * realistic fake data used in testing, development, or demo scenarios.
 * <p>
 * By declaring a {@code Faker} bean, other components and services can inject
 * and reuse the same Faker instance rather than creating multiple instances.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * @Autowired
 * private Faker faker;
 *
 * public void generateUserData() {
 *     String firstName = faker.name().firstName();
 *     String email = faker.internet().emailAddress();
 *     // ...
 * }
 * }</pre>
 * </p>
 *
 * @see Faker
 */
@Configuration
public class FakerConfig {

    /**
     * Defines the Faker bean to be used application-wide.
     * <p>
     * The returned {@link Faker} is configured with the default locale
     * and can be injected wherever fake data generation is needed.
     * </p>
     *
     * @return a new {@link Faker} instance
     */
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
