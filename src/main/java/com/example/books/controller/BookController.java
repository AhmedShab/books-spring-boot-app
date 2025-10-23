package com.example.books.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.books.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

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

    @GetMapping("/books")
    public List<Book> getBookByCategory(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/books")
    public void postBook(@RequestBody Book newBook) {
        books.stream()
             .filter(b -> b.getTitle().equalsIgnoreCase(newBook.getTitle()))
             .findFirst()
             .ifPresent(existingBook -> {
                 return;
             });

        books.add(newBook);
    }

    @PutMapping("books/{id}")
    public void putBook(@PathVariable Integer id, @RequestBody String newTitle) {
         Optional<Book> updateBook = books.stream()
            .filter(book -> book.getId() == id)
            .findFirst();

            updateBook.ifPresent(b -> b.setTitle(newTitle));
    }
}
