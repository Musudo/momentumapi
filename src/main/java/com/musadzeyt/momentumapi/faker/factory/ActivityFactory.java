package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.enums.ActivityTypeEnum;
import com.musadzeyt.momentumapi.repository.IInstitutionRepository;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ActivityFactory {
    private final Faker faker;
    private final IUserRepository userRepository;
    private final IInstitutionRepository institutionRepository;
    private final ITagRepository tagRepository;

    /**
     * Creates an Activity with fake data.
     *
     * @return a new Activity instance with default values.
     */
    public Activity create(ActivityTypeEnum activityType, int atMost, int minimum) {
        LocalDateTime startTime = LocalDateTime.parse(
                faker.date().future(atMost, minimum, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        Random random = new Random();

        return Activity.builder()
                .subject(faker.name().title())
                .startTime(startTime)
                .endTime(startTime.plusHours(1))
                .externalNote(faker.lorem().paragraph(2))
                .internalNote(faker.lorem().paragraph(2))
                .type(activityType)
                .tags(
                        faker.options().option(tagRepository.findAll().subList(0, 3))
                )
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .institution(
                        faker.options().option(institutionRepository.findAll()).get(random.nextInt(5))
                )
                .createdAt(
                        LocalDateTime.parse(faker.date().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }

    /**
     * Optional: Customize the activity after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Activity initialize(Activity activity) {
        // Perform any post-processing if needed
        return activity;
    }
}
