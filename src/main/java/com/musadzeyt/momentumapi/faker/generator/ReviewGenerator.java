package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.faker.factory.ReviewFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ReviewGenerator {
    private final ReviewFactory reviewFactory;

    /**
     * Creates a list of reviews using the ReviewsFactory.
     *
     * @param count the number of reviews to generate.
     * @return a List of generated Review objects.
     */
    public List<Review> createReviews(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> reviewFactory.create())
                .collect(Collectors.toList());
    }
}
