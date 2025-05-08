package com.musadzeyt.momentumapi.dataFaker.factory;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
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
public class TaskFactory {
    private final Faker faker;
    private final IActivityRepository activityRepository;
    private final IUserRepository userRepository;

    /**
     * Creates a Task with fake data.
     *
     * @return a new Task instance with default values.
     */
    public Task create() {
        Random random = new Random();

        return Task.builder()
                .description(faker.options().option("testTask1", "testTask2", "testTask3"))
                .completed(faker.bool().bool())
                .activity(activityRepository.findAll().get(random.nextInt(5)))
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .createdAt(
                        LocalDateTime.parse(faker.timeAndDate().past(30, 0, TimeUnit.DAYS, "yyyy-MM-dd HH:mm:ss"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .build();
    }
}
