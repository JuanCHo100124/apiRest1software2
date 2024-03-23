package com.software2.apirest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.apirest.model.User;
import java.util.*;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<User> users;

    public UserController() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User[] userArray = objectMapper.readValue(new ClassPathResource("users.json").getFile(), User[].class); 
            users = new ArrayList<>(Arrays.asList(userArray));

        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    @GetMapping
    public List<User> getAllUsers(){
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        users.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updateUser) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(updateUser.getName());
            user.setEmail(updateUser.getEmail());
            return user;    
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        users.removeIf(users -> users.getId().equals(id));
    }
}
