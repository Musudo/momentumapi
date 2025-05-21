package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dataFaker.factory.VoiceMemoFactory;
import com.musadzeyt.momentumapi.repository.IVoiceMemoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class VoiceMemoGenerator {
    private final VoiceMemoFactory voiceMemoFactory;
    private final IVoiceMemoRepository voiceMemoRepository;

    /**
     * Creates a list of voiceMemos using the VoiceMemoFactory.
     *
     * @param count the number of voiceMemos to generate.
     * @return a List of generated VoiceMemo objects.
     */
    public List<VoiceMemo> createVoiceMemos(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> voiceMemoRepository.save(voiceMemoFactory.create()))
                .collect(Collectors.toList());
    }
}
