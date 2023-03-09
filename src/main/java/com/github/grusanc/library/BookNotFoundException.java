package com.github.grusanc.library;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("The catalogue doesn't have such a book in its catalogue!");
    }
}
