package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class TagFactory {
    private final Faker faker;

    /**
     * Creates a Tag with fake data.
     *
     * @return a new Tag instance with default values.
     */
    public Tag create(TagNameEnum tagName) {
        return Tag.builder()
                .name(tagName)
                .createdAt(
                        LocalDateTime.parse(faker.date().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }

    /**
     * Optional: Customize the tag after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Tag initialize(Tag tag) {
        // Perform any post-processing if needed
        return tag;
    }
}
