package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.Review;
import com.musadzeyt.momentumapi.repository.ActivityRepository;
import com.musadzeyt.momentumapi.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ReviewFactory {
    private final Faker faker;
    private final ActivityRepository activityRepository;
    private final AppUserRepository userRepository;

    /**
     * Creates a Review with fake data.
     *
     * @return a new Review instance with default values.
     */
    public Review create() {
        return Review.builder()
                .title(faker.book().title())
                .content(faker.lorem().sentence())
                .activity(activityRepository.findAll().getFirst())
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .createdAt(
                        LocalDateTime.parse(faker.timeAndDate().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }
}
