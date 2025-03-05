package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.dto.UserDto;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.record.UserRegistrationRequestRecord;
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserDtoById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public String registerUser(UserRegistrationRequestRecord request) {
        if (userRepository.findByEmail(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // Hash the password before saving it

        userRepository.save(user);

        return user.getEmail();
    }

    public User create(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        return userRepository.save(user);
    }

    public User update(UUID id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        userMapper.update(userDto, user);
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
