package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.AppUserDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.record.UserRegistrationRequest;
import com.musadzeyt.momentumapi.repository.AppUserRepository;
import com.musadzeyt.momentumapi.util.mapper.AppUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository userRepository;
    private final AppUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // Hash the password before saving it

        userRepository.save(user);

        return user.getEmail();
    }

    @Transactional
    public AppUser create(AppUserDto userDto) {
        AppUser user = userMapper.dtoToEntity(userDto);
        return userRepository.save(user);
    }

    @Transactional
    public AppUser update(UUID id, AppUserDto userDto) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        userMapper.update(userDto, user);
        return userRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
