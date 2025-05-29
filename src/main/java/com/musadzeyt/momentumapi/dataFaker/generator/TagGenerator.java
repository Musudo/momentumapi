package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.dataFaker.factory.TagFactory;
import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagName;
import com.musadzeyt.momentumapi.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TagGenerator {
    private final TagFactory tagFactory;
    private final TagRepository tagRepository;

    public List<Tag> createTags() {
        List<TagName> tagNames = List.of(
                TagName.PERSONAL,
                TagName.WORK,
                TagName.FINANCE,
                TagName.EDUCATION,
                TagName.FAMILY,
                TagName.TRAINING
        );

        List<Tag> tags = tagNames.stream()
                .map(tagFactory::create)
                .collect(Collectors.toList());

        tagRepository.saveAll(tags);

        return tags;
    }
}
