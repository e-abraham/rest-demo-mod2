package com.example.restdemo.controllers;

import com.example.restdemo.exceptions.ResourceNotFoundException;
import com.example.restdemo.models.Author;
import com.example.restdemo.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("id/{id}")
    //get author by id from path
    public ResponseEntity<Author> getAuthorById(@PathVariable long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " + id + " does not exist"));
        // ResponseEntity adds status code
        return ResponseEntity.ok(author);
    }

    @PutMapping("id/{id}")
    //update author by id from path and request body
    public ResponseEntity<Author> updateAuthor(@PathVariable long id, @RequestBody Author author){
        Author updateAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " + id + " does not exist"));
        updateAuthor.setFirstName(author.getFirstName());
        updateAuthor.setLastName(author.getLastName());
        updateAuthor.setGenre(author.getGenre());

        authorRepository.save(updateAuthor);

        return ResponseEntity.ok(updateAuthor);
    }

    @DeleteMapping("id/{id}")
    //delete book by id from path
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " + id + " does not exist"));

        authorRepository.delete(author);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
