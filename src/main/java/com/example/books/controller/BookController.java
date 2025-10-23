package com.example.books.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class BookController {

    @GetMapping("/api")
    public String firstAPI() {
        return "Hello Ahmed";
    }
    
}
