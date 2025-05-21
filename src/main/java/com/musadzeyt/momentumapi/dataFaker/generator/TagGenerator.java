package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.dataFaker.factory.TagFactory;
import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TagGenerator {
    private final TagFactory tagFactory;
    private final ITagRepository tagRepository;

    public List<Tag> createTags() {
        List<TagNameEnum> tagNames = List.of(
                TagNameEnum.PERSONAL,
                TagNameEnum.WORK,
                TagNameEnum.FINANCE,
                TagNameEnum.EDUCATION,
                TagNameEnum.FAMILY,
                TagNameEnum.TRAINING
        );

        List<Tag> tags = tagNames.stream()
                .map(tagFactory::create)
                .collect(Collectors.toList());

        tagRepository.saveAll(tags);

        return tags;
    }
}
