package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import com.musadzeyt.momentumapi.dataFaker.factory.ReviewEmailFactory;
import com.musadzeyt.momentumapi.repository.IReviewEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ReviewEmailGenerator {
    private final ReviewEmailFactory reviewEmailFactory;
    private final IReviewEmailRepository reviewEmailRepository;

    /**
     * Creates a list of emails using the EmailFactory.
     *
     * @param count the number of emails to generate.
     * @return a List of generated Email objects.
     */
    public List<ReviewEmail> createEmails(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> reviewEmailRepository.save(reviewEmailFactory.create()))
                .collect(Collectors.toList());
    }
}
