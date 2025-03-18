package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.ReviewAttachment;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewAttachmentFactory {
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
