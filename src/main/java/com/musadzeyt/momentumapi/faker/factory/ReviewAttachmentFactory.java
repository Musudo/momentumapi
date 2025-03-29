package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.ReviewAttachment;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ReviewAttachmentFactory {
    private final Faker faker;
    private final IReviewRepository reviewRepository;

    /**
     * Creates an Attachment with fake data.
     *
     * @return a new Attachment instance with default values.
     */
    public ReviewAttachment create() {
        return ReviewAttachment.builder()
                .path("/test/path")
                .review(reviewRepository.findAll().getFirst())
                .createdAt(
                        LocalDateTime.parse(faker.date().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }

    /**
     * Optional: Customize the attachment after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public ReviewAttachment initialize(ReviewAttachment reviewAttachment) {
        // Perform any post-processing if needed
        return reviewAttachment;
    }
}
