package com.user.service.api.controllers;

import com.common.security.dtos.UserDTO;
import com.common.security.utility.JsonWebTokenUtility;
import com.user.service.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestHeader("Authorization") String auth,@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestHeader("Authorization") String auth,@PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/by-Id/{userId}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String auth,@PathVariable String userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestHeader("Authorization") String auth) {
        System.out.println("@@@ Auth :::: "+ JsonWebTokenUtility.extractUsername(auth.substring(7)));
        List<UserDTO> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/updateUserById/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String auth,@PathVariable String userId, @RequestBody UserDTO userDTO) {
        var updatedUser = userService.updateUserById(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String auth,@PathVariable String userId){
       userService.deleteUser(userId);
        return new ResponseEntity<>(Map.of("success", true, "statusCode", 200, "message", "User has been deleted"), HttpStatus.OK);
    }
}
