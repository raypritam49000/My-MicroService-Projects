package com.user.service.api.services.impl;

import com.user.service.api.dtos.UserDTO;
import com.user.service.api.entities.UserEntity;
import com.user.service.api.exceptions.ResourceNotFoundException;
import com.user.service.api.mappers.UserMapper;
import com.user.service.api.repositories.UserRepository;
import com.user.service.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return UserMapper.INSTANCE.toDto(userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username : "+username)));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserMapper.INSTANCE.toDto(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email : "+email)));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        return UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDTO)));
    }


    @Override
    public boolean userExistsByEmail(String email) {
        // Check if the user with the given email already exists in the database
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public boolean userExistsByUsername(String username) {
        // Check if the user with the given username already exists in the database
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }

    @Override
    public List<UserDTO> getUsers() {
        return UserMapper.INSTANCE.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        return UserMapper.INSTANCE.toDto(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with userId : "+userId)));
    }

    @Override
    public UserDTO updateUserById(String userId, UserDTO userDto) {
        UserDTO existUser = getUserByUserId(userId);
        existUser.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
        existUser.setEmail(userDto.getEmail());
        existUser.setUsername(userDto.getUsername());
        return UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(existUser)));
    }

    @Override
    public void deleteUser(String userId) {
        UserDTO existUser = getUserByUserId(userId);
        userRepository.delete(UserMapper.INSTANCE.toEntity(existUser));
    }


}
