package com.github.grusanc.library;

public class BookAlreadyLentException extends RuntimeException {

    public BookAlreadyLentException() {
        super("The reader already borrowed this book!");
    }
}
