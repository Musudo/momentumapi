package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.dataFaker.factory.TagFactory;
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
        Tag personalTag = tagFactory.create(TagNameEnum.PERSONAL);
        Tag workTag = tagFactory.create(TagNameEnum.WORK);
        Tag financeTag = tagFactory.create(TagNameEnum.FINANCE);
        Tag educationTag = tagFactory.create(TagNameEnum.EDUCATION);
        Tag familyTag = tagFactory.create(TagNameEnum.FAMILY);
        Tag trainingTag = tagFactory.create(TagNameEnum.TRAINING);

        tags.add(personalTag);
        tags.add(workTag);
        tags.add(financeTag);
        tags.add(educationTag);
        tags.add(familyTag);
        tags.add(trainingTag);

        return tags;
    }
}
