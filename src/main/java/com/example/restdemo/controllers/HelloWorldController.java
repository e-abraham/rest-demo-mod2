package com.example.restdemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(value="/rest-demo/hello")
    public String sayHello(){

        return "Hello World!";

    }

    @GetMapping(value="/rest-demo/hello/name/{name}")
    public String sayHello(@PathVariable(name = "name", required = true) String name){

        return "Hello " + name + "!";

    }
}
