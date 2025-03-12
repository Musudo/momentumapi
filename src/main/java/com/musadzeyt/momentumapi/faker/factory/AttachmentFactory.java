package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttachmentFactory {
    private final IReviewRepository reviewRepository;

    /**
     * Creates an Attachment with fake data.
     *
     * @return a new Attachment instance with default values.
     */
    public Attachment create() {
        return Attachment.builder()
                .path("/test/path")
                .review(reviewRepository.findAll().getFirst())
                .build();
    }

    /**
     * Optional: Customize the attachment after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Attachment initialize(Attachment attachment) {
        // Perform any post-processing if needed
        return attachment;
    }
}
