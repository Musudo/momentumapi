package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Email;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailFactory {
    private final IReviewRepository reviewRepository;

    /**
     * Creates an Email with fake data.
     *
     * @return a new Email instance with default values.
     */
    public Email create() {
        return Email.builder()
                .email("test@email.com")
                .review(reviewRepository.findAll().getFirst())
                .build();
    }

    /**
     * Optional: Customize the email after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Email initialize(Email email) {
        // Perform any post-processing if needed
        return email;
    }
}
