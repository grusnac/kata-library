package com.github.grusanc.library;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class BookCatalogue {
    private final Map<Book, Integer> catalogue;
    private final Map<String, Book> isbnCatalogue;

    public BookCatalogue(final Map<Book, Integer> catalogue) {
        this.catalogue = new HashMap<>(catalogue);
        this.isbnCatalogue = buildIsbnCatalogue(catalogue);
    }

    public BookCatalogue() {
        this(Map.of());
    }

    private static Map<String, Book> buildIsbnCatalogue(final Map<Book, Integer> catalogue) {
        return catalogue.keySet()
                .stream()
                .collect(toMap(Book::isbn, Function.identity()));
    }

    private void rebuildIsbnCatalogue() {
        this.isbnCatalogue.clear();
        this.isbnCatalogue.putAll(buildIsbnCatalogue(this.catalogue));
    }

    public void addBook(final Book book) {
        catalogue.put(book, 1);
        rebuildIsbnCatalogue();
    }

    public void addBook(final Book book, final int amountOfBooks) {
        catalogue.put(book, amountOfBooks);
        rebuildIsbnCatalogue();
    }

    public int getBookCount(final Book book) {
        return catalogue.getOrDefault(book, 0);
    }

    public Book giveBook(final String isbn) {
        if (isbnCatalogue.containsKey(isbn)) {
            final Book book = isbnCatalogue.get(isbn);
            catalogue.computeIfPresent(book, (key, amount) -> --amount);
            return book;
        }
        throw new BookNotFoundException();
    }

    public void takeBook(final Book book) {
        if (!catalogue.containsKey(book)) {
            throw new UnknownBookException();
        } else {
            catalogue.computeIfPresent(book, (key, amount) -> ++amount);
        }
    }
}
