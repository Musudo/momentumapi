package com.musadzeyt.momentumapi.dataFaker.factory;

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
                        LocalDateTime.parse(faker.timeAndDate().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }
}
