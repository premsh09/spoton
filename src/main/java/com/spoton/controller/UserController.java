package com.spoton.controller;

import com.spoton.dto.LoginDto;
import com.spoton.dto.PropertyUserDto;
import com.spoton.dto.TokenResponse;
import com.spoton.entity.PropertyUser;
import com.spoton.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser propertyUser = userService.addUser(propertyUserDto);
        if (propertyUser!=null){
            return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
       String token = userService.verifyLogin(loginDto);
       if(token!=null){
           TokenResponse tokenResponse = new TokenResponse();
           tokenResponse.setToken(token);
           return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
       }
       return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;
    }
}
