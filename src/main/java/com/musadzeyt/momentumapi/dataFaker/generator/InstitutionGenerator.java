package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.dataFaker.factory.InstitutionFactory;
import com.musadzeyt.momentumapi.repository.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class InstitutionGenerator {
    private final InstitutionFactory institutionFactory;
    private final InstitutionRepository institutionRepository;

    /**
     * Creates a list of institutions using the InstitutionFactory.
     *
     * @param count the number of institutions to generate.
     * @return a List of generated Institution objects.
     */
    public List<Institution> createInstitutions(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> institutionRepository.save(institutionFactory.create()))
                .collect(Collectors.toList());
    }
}
