package com.example.restdemo.controllers;

import com.example.restdemo.exceptions.ResourceNotFoundException;
import com.example.restdemo.models.Book;
import com.example.restdemo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//set default handle
@RequestMapping("/rest-demo/books")
public class BookController {

    //get book repo through dependency injection
    @Autowired
    private BookRepository bookRepository;

    //get all books
    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @PostMapping
    //create new book from request body
    public Book createBook(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping("id/{id}")
    //get book by id from path
    public ResponseEntity<Book> getBookById(@PathVariable long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " does not exist"));
        // ResponseEntity adds status code
        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    //update book by id from path and request body
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book){
        Book updateBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " does not exist"));
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setGenre(book.getGenre());
        updateBook.setYear(book.getYear());
        updateBook.setCoverImg(book.getCoverImg());

        bookRepository.save(updateBook);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("{id}")
    //delete book by id from path
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " does not exist"));

        bookRepository.delete(book);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
