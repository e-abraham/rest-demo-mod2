package com.example.restdemo.controllers;

import com.example.restdemo.exceptions.ResourceNotFoundException;
import com.example.restdemo.models.Author;
import com.example.restdemo.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//set default handle
@RequestMapping("/rest-demo/authors")
public class AuthorController {

    //get author repo through dependency injection
    @Autowired
    private AuthorRepository authorRepository;

    //get all authors
    @GetMapping
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @PostMapping
    //create new author from request body
    public Author createAuthor(@RequestBody Author author){
        return authorRepository.save(author);
    }

//    @GetMapping("{id}")
//    //get author by id from path
//    public ResponseEntity<Author> getAuthorById(@PathVariable long id){
//        Author author = authorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(""))
//    }

}
