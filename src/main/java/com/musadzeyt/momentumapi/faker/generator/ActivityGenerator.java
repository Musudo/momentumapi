package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.enums.ActivityTypeEnum;
import com.musadzeyt.momentumapi.faker.factory.ActivityFactory;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ActivityGenerator {
    private final ActivityFactory activityFactory;
    private final Faker faker;

    /**
     * Creates a list of activities using the ActivityFactory.
     *
     * @param count the number of activities to generate.
     * @return a List of generated Activity objects.
     */
    public List<Activity> createActivities(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> activityFactory.create(faker.options().option(ActivityTypeEnum.values())))
                .collect(Collectors.toList());
    }
}
