package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.dto.UserDto;
import com.musadzeyt.momentumapi.exception.GeneralException;
import com.musadzeyt.momentumapi.repository.IUserRepository;
import com.musadzeyt.momentumapi.util.mapper.IUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

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
