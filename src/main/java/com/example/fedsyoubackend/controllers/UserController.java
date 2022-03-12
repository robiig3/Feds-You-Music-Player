package com.example.fedsyoubackend.controllers;

import com.example.fedsyoubackend.model.User;
import com.example.fedsyoubackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(value ="/login", consumes = "multipart/form-data")
    public String verifyUser(@RequestPart("username")String username, @RequestPart("password")String password){

        if(userRepository.existsUserByUsernameEquals(username)){
            User user = userRepository.findUserByUsername(username);
            user.setActive(true);
            if(user.getRoles().equals("ADMIN")){
                return "redirect:/adminHome";
            }
            return "redirect:/userHome";
        }
        System.out.println("User not founded");
        return "redirect:/";
    }

    @PostMapping(value ="/register", consumes = "multipart/form-data")
    public String registerUser(@RequestPart("username")String username, @RequestPart("password")String password){

        if(!userRepository.existsUserByUsernameEquals(username)){
            User user = new User(username, password, "USER");

            userRepository.insert(user);

            return "redirect:/";
        }
        System.out.println("The username is already taken.");
        return "redirect:/register";
    }

}
