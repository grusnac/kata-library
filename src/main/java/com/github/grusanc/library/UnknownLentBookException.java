package com.github.grusanc.library;

public class UnknownLentBookException extends RuntimeException {

    public UnknownLentBookException() {
        super("The library doesn't have any record of this book being lent!");
    }
}
