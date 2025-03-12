package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.faker.factory.TagFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TagGenerator {
    private final TagFactory tagFactory;

    public List<Tag> createTags() {
        List<Tag> tags = new ArrayList<>();
        Tag workshopTag = tagFactory.create(TagNameEnum.WORKSHOP);
        Tag networkingTag = tagFactory.create(TagNameEnum.NETWORKING);
        Tag financeTag = tagFactory.create(TagNameEnum.FINANCE);

        tags.add(workshopTag);
        tags.add(networkingTag);
        tags.add(financeTag);

        return tags;
    }
}
