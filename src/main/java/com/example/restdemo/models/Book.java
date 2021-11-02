package com.example.restdemo.models;

import lombok.Data;

import javax.persistence.*;

//Generates getters, setters, constructor
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "year")
    private Integer year;

    @Column(name = "cover_img")
    private String coverImg;

}
