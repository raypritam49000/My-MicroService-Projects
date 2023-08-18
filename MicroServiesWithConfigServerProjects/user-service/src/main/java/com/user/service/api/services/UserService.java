package com.user.service.api.services;

import com.user.service.api.dtos.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO getUserByUsername(String username);

    public UserDTO getUserByEmail(String email);

    public UserDTO createUser(UserDTO userDTO);

    public boolean userExistsByEmail(String email);

    public boolean userExistsByUsername(String username);

    public List<UserDTO> getUsers();

    public UserDTO getUserByUserId(String userId);

    public UserDTO updateUserById(String userId,UserDTO userDto);

    public void deleteUser(String userId);
}
