package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.dto.UserDto;
import com.musadzeyt.momentumapi.dto.UserRegistrationRequestDto;
import com.musadzeyt.momentumapi.exception.GeneralException;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import com.musadzeyt.momentumapi.util.mapper.IUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.entityListToDtoList(users);
    }

    public UserDto findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(GeneralException::new);
        return userMapper.entityToDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(GeneralException::new);
        return userMapper.entityToDto(user);
    }

    @Transactional
    public String registerUser(UserRegistrationRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setFirstName(request.getEmail());
        user.setLastName(request.getEmail());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password before saving it

        userRepository.save(user);
        return "User registered successfully";
    }

    public User create(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        return userRepository.save(user);
    }

    public User update(UUID id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(GeneralException::new);
        userMapper.update(userDto, user);
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
