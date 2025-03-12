package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Email;
import com.musadzeyt.momentumapi.faker.factory.EmailFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class EmailGenerator {
    private final EmailFactory emailFactory;

    /**
     * Creates a list of emails using the EmailFactory.
     *
     * @param count the number of emails to generate.
     * @return a List of generated Email objects.
     */
    public List<Email> createEmails(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> emailFactory.create())
                .collect(Collectors.toList());
    }
}
