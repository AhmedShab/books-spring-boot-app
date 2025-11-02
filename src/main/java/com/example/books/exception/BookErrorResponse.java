package com.example.books.exception;

public class BookErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public BookErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    
}
