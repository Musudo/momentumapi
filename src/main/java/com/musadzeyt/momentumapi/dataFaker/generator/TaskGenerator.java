package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dataFaker.factory.TaskFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class TaskGenerator {
    private final TaskFactory taskFactory;

    /**
     * Creates a list of tasks using the TaskFactory.
     *
     * @param count the number of tasks to generate.
     * @return a List of generated Task objects.
     */
    public List<Task> createTasks(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> taskFactory.create())
                .collect(Collectors.toList());
    }
}
