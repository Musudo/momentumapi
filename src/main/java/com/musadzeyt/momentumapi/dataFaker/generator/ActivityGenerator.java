package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.enums.ActivityTypeEnum;
import com.musadzeyt.momentumapi.dataFaker.factory.ActivityFactory;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ActivityGenerator {
    private final ActivityFactory activityFactory;
    private final Faker faker;

    /**
     * Creates a list of activities for today using the ActivityFactory.
     *
     * @param count the number of activities to generate.
     * @return a List of generated Activity objects for today.
     */
    public List<Activity> createActivitiesToday(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> activityFactory.create(faker.options().option(ActivityTypeEnum.values()), 1, 0))
                .collect(Collectors.toList());
    }

    /**
     * Creates a list of activities for next seven days using the ActivityFactory.
     *
     * @param count the number of activities to generate.
     * @return a List of generated Activity objects for next seven days.
     */
    public List<Activity> createActivitiesNextSevenDays(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> activityFactory.create(faker.options().option(ActivityTypeEnum.values()), 8, 1))
                .collect(Collectors.toList());
    }

    /**
     * Creates a list of activities for next thirty days using the ActivityFactory.
     *
     * @param count the number of activities to generate.
     * @return a List of generated Activity objects for next thirty days.
     */
    public List<Activity> createActivitiesNextThirtyDays(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> activityFactory.create(faker.options().option(ActivityTypeEnum.values()), 38, 8))
                .collect(Collectors.toList());
    }
}
