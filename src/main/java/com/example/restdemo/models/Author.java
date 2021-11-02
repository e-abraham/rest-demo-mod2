package com.example.restdemo.models;

import lombok.Data;

import javax.persistence.*;

//Generates getters, setters, constructor
@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "genre")
    private String genre;

}
