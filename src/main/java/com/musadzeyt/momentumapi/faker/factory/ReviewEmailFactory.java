package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ReviewEmailFactory {
    private final Faker faker;
    private final IReviewRepository reviewRepository;

    /**
     * Creates an Email with fake data.
     *
     * @return a new Email instance with default values.
     */
    public ReviewEmail create() {
        return ReviewEmail.builder()
                .email("test@email.com")
                .review(reviewRepository.findAll().getFirst())
                // TODO: fix createdAt not being correct
                .createdAt(
                        LocalDateTime.parse(faker.date().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }

    /**
     * Optional: Customize the email after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public ReviewEmail initialize(ReviewEmail reviewEmail) {
        // Perform any post-processing if needed
        return reviewEmail;
    }
}
