package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor
public class ReviewFactory {
    private final Faker faker;
    private final IActivityRepository activityRepository;
    private final IUserRepository userRepository;

    /**
     * Creates a Review with fake data.
     *
     * @return a new Review instance with default values.
     */
    public Review create() {
        Random random = new Random();

        return Review.builder()
                .title(faker.book().title())
                .content(faker.lorem().sentence())
                .activity(activityRepository.findAll().get(random.nextInt(5)))
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .build();
    }

    /**
     * Optional: Customize the review after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Review initialize(Review review) {
        // Perform any post-processing if needed
        return review;
    }
}
