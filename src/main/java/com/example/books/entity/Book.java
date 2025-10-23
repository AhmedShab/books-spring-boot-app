package com.example.books.entity;

public class Book {
    // I want to auto generate id
    private static int idCounter = 0;
    private Integer id;
    private String title;
    private String author;
    private String category;

    public Book(String title, String author, String category) {
        this.id = ++idCounter;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", category=" + category + "]";
    }
}
