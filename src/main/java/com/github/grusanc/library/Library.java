package com.github.grusanc.library;

import java.util.*;

public class Library {

    private final BookCatalogue catalogue;
    private final Map<Reader, Set<Book>> borrowedBooks;

    public Library(BookCatalogue catalogue) {
        this.catalogue = catalogue;
        this.borrowedBooks = new HashMap<>();
    }

    public void addBook(final Book book) {
        catalogue.addBook(book);
    }

    public void addBook(final Book book, final int amountOfBooks) {
        catalogue.addBook(book, amountOfBooks);
    }

    public void lendBook(final String isbn, final Reader reader) {
        final Book book = catalogue.giveBook(isbn);
        if (borrowedBooks.containsKey(reader)) {
            if (borrowedBooks.get(reader).contains(book)) {
                throw new BookAlreadyLentException();
            }
            borrowedBooks.get(reader).add(book);
        } else {
            borrowedBooks.put(reader, new HashSet<>(Set.of(book)));
        }
    }

    public void returnBook(final Book book, final Reader reader) {
        if (!borrowedBooks.containsKey(reader) || !borrowedBooks.get(reader).contains(book)) {
            throw new UnknownLentBookException();
        }
        if (borrowedBooks.get(reader).remove(book)) {
            catalogue.takeBook(book);
        }
        if (borrowedBooks.get(reader).isEmpty()) {
            borrowedBooks.remove(reader);
        }
    }

    public void showBorrowedBooks(final Reader reader) {
        if (borrowedBooks.containsKey(reader)) {
            System.out.println("Given reader has the following borrowed books:");
            borrowedBooks.get(reader).forEach(System.out::println);
        } else {
            System.out.println("Given reader has no borrowed books.");
        }
    }
}
