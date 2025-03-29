package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Random;

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
                .build();
    }

    /**
     * Optional: Customize the task after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public Task initialize(Task task) {
        // Perform any post-processing if needed
        return task;
    }
}
