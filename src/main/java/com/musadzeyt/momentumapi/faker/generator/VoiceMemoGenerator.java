package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.faker.factory.VoiceMemoFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class VoiceMemoGenerator {
    private final VoiceMemoFactory voiceMemoFactory;

    /**
     * Creates a list of voiceMemos using the VoiceMemoFactory.
     *
     * @param count the number of voiceMemos to generate.
     * @return a List of generated VoiceMemo objects.
     */
    public List<VoiceMemo> createVoiceMemos(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> voiceMemoFactory.create())
                .collect(Collectors.toList());
    }
}
