package com.example.restdemo.services;

import com.example.restdemo.exceptions.ResourceNotFoundException;
import com.example.restdemo.models.User;
import com.example.restdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " does not exist"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user){
        user.setPassword(Hasher.hash(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users){
        for (User user: users){
            user.setPassword(Hasher.hash(user.getPassword()));
        }
        return userRepository.saveAll(users);
    }

    public User updateUser(Long id, User userUpdate){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " does not exist"));
        user.setUsername(userUpdate.getUsername());
        user.setPassword(userUpdate.getPassword());
        user.setEmailAddress(userUpdate.getEmailAddress());

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " does not exist"));
//        userRepository.delete(user);
        userRepository.deleteById(id);
    }
}
