package com.github.grusanc.library;

public class UnknownBookException extends RuntimeException {

    public UnknownBookException() {
        super("The catalogue doesn't about this book!");
    }
}
