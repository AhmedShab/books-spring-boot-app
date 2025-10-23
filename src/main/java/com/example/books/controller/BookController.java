package com.example.books.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.books.entity.Book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Fiction"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("A Brief History of Time", "Stephen Hawking", "Science"),
            new Book("The Art of War", "Sun Tzu", "Philosophy")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBookByCategory(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
