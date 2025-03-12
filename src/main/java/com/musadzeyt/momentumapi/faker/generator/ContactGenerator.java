package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.faker.factory.ContactFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ContactGenerator {
    private final ContactFactory contactFactory;

    /**
     * Creates a list of contacts using the ContactFactory.
     *
     * @param count the number of contacts to generate.
     * @return a List of generated Contact objects.
     */
    public List<Contact> createContacts(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> contactFactory.create())
                .collect(Collectors.toList());
    }
}
