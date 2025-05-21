package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class TagFactory {

    /**
     * Creates a Tag with fake data.
     *
     * @return a new Tag instance with default values.
     */
    public Tag create(TagNameEnum tagName) {
        return Tag.builder()
                .name(tagName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
