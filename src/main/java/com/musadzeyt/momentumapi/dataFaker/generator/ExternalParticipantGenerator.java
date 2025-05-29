package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.dataFaker.factory.ExternalParticipantFactory;
import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ExternalParticipantGenerator {
    private final ExternalParticipantFactory externalParticipantFactory;

    /**
     * Creates a list of external participants using the ExternalParticipantFactory.
     *
     * @param count the number of external participants to generate.
     * @return a List of generated ExternalParticipant objects.
     */
    public List<ExternalParticipant> createExternalParticipants(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> externalParticipantFactory.create())
                .collect(Collectors.toList());
    }
}
