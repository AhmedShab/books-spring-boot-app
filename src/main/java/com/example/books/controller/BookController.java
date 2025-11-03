package com.example.books.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.books.entity.Book;
import com.example.books.exception.BookNotFoundException;
import com.example.books.request.BookRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Books Rest API endpoints", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 3),
                new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 10),
                new Book(3, "1984", "George Orwell", "Dystopian", 9),
                new Book(4, "A Brief History of Time", "Stephen Hawking", "Science", 8),
                new Book(5, "The Art of War", "Sun Tzu", "Philosophy", 7)));
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Book> getBooks(
            @Parameter(description = "Optional query param", required = false) @RequestParam(required = false) String category) {
        if (category == null) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Operation(summary = "Get a book by Id", description = "Retrieve a specific book by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(
            @Parameter(description = "Id of book to be retrieved") @PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found - " + id));
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public void postBook(@Valid @RequestBody BookRequest newBook) {
        long id = books.isEmpty() ? 1 : books.get(books.size() - 1).getId() + 1;

        Book book = convertToBook(id, newBook);
        books.add(book);
    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book putBook(@PathVariable @Min(value = 1) long id, @Valid @RequestBody BookRequest requestBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book updatedBook = convertToBook(id, requestBook);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }

        throw new BookNotFoundException("Book not found with id: " + id);
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(
            @Parameter(description = "Id of the book to delete") @PathVariable @Min(value = 1) long id) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating());
    }
}
