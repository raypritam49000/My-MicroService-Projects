package com.user.service.api;

import com.user.service.api.dtos.AuthenticationDTO;
import com.user.service.api.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceApi {

    @PostMapping("/auth/api/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO);

    @PostMapping("/auth/api/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationDTO authenticationDTO);

    @GetMapping("/users/by-username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);

    @GetMapping("/users/by-email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);

    @GetMapping("/users/by-Id/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId);

    @GetMapping("/users/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers();

    @PutMapping("/users/updateUserById/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO);

    @DeleteMapping("/users/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId);
}
