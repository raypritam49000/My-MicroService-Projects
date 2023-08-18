package com.user.service.api.controllers;

import com.common.security.dtos.UserDTO;
import com.common.security.utility.JsonWebTokenUtility;
import com.common.security.utility.PasswordEncoder;
import com.user.service.api.dtos.ApiResponse;
import com.user.service.api.dtos.AuthTokenDTO;
import com.user.service.api.dtos.AuthenticationDTO;
import com.user.service.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth/api")
public class AuthController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/hello")
    public String Hello() {
        return "Hello Pritam Ray";
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        boolean userExistByUsername = userService.userExistsByUsername(userDTO.getUsername());

        if (userExistByUsername) {
            return new ResponseEntity<>(Map.of("success", false, "statusCode", 409, "message", "User already exists with username : " + userDTO.getUsername()), HttpStatus.CONFLICT);
        }

        boolean userExistByEmail = userService.userExistsByEmail(userDTO.getEmail());

        if (userExistByEmail) {
            return new ResponseEntity<>(Map.of("success", false, "statusCode", 409, "message", "User already exists with email : " + userDTO.getEmail()), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {

        boolean userExistByUsername = userService.userExistsByUsername(authenticationDTO.getUsername().trim());

        if (!userExistByUsername) {
            return new ResponseEntity<>(new ApiResponse(true,"Not Found",404,"User does not found with username : " + authenticationDTO.getUsername()), HttpStatus.OK);
        }


        UserDTO userDTO = userService.getUserByUsername(authenticationDTO.getUsername());

        boolean isMatchPassword = PasswordEncoder.matchPassword(authenticationDTO.getPassword(),userDTO.getPasswordHash());

        if(!isMatchPassword){
            return new ResponseEntity<>(new ApiResponse(false,"Bad Credentials",403,"Password does not match!!!"), HttpStatus.OK);
        }

        // Create auth token
        String jwt = JsonWebTokenUtility.generateToken(userDTO);

        return new ResponseEntity<>(new AuthTokenDTO(jwt), HttpStatus.OK);
    }


}
