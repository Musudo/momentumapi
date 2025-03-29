package com.musadzeyt.momentumapi.faker.factory;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoiceMemoFactory {
    private final IUserRepository userRepository;

    /**
     * Creates a VoiceMemo with fake data.
     *
     * @return a new VoiceMemo instance with default values.
     */
    public VoiceMemo create() {
        return VoiceMemo.builder()
                .path("/test/path")
                .user(userRepository.findByEmail("guest@email.com").orElse(null))
                .build();
    }

    /**
     * Optional: Customize the voiceMemo after instantiation.
     * This method is analogous to the `afterInstantiate` hook in Zenstruck Foundry.
     */
    public VoiceMemo initialize(VoiceMemo voiceMemo) {
        // Perform any post-processing if needed
        return voiceMemo;
    }
}
