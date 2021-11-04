package com.example.restdemo.controllers;

import com.example.restdemo.models.User;
//import com.example.restdemo.services.HasherService;
import com.example.restdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest-demo/users")
public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    HasherService hasherService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
//        String hashPassword = hasherService.hash(user.getPassword());
//        user.setPassword(hashPassword);
        return userService.saveUser(user);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = userService.getUserById(id);
        //ResponseEntity adds status code
        return ResponseEntity.ok(user);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userUpdate){
        User updateUser = userService.updateUser(id, userUpdate);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
