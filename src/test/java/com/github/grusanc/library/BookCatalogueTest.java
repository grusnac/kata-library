package com.github.grusanc.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookCatalogueTest {
    private final Book hpjp = new Book( "973022823X", "High-Performance Java Persistence");
    private final Book lotr = new Book("9780544003415", "The Lord Of The Rings");
    private final Book ej = new Book("0134685997", "Effective Java");

    @Nested
    class AddBooks {

        private final BookCatalogue bookCatalogue = new BookCatalogue();

        @Test
        void mustAddOneBook() {
            bookCatalogue.addBook(hpjp);
            assertEquals(1, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void mustAddTreeBooks() {
            bookCatalogue.addBook(hpjp, 3);
            assertEquals(3, bookCatalogue.getBookCount(hpjp));
        }
    }

    @Nested
    class BarrowBooks {

        private final BookCatalogue bookCatalogue = new BookCatalogue();

        @BeforeEach
        void addBooks() {
            bookCatalogue.addBook(hpjp, 3);
            bookCatalogue.addBook(lotr, 10);
        }

        @Test
        void shouldBorrowOne_HPJP() {
            final Book book = bookCatalogue.giveBook(hpjp.isbn());
            assertNotNull(book);
            assertEquals(2, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void shouldBorrowOne_LOTR() {
            final Book book = bookCatalogue.giveBook(lotr.isbn());
            assertNotNull(book);
            assertEquals(9, bookCatalogue.getBookCount(lotr));
        }

        @Test
        void tryBarrow_EJ() {
            final String isbn = ej.isbn();
            assertThrows(BookNotFoundException.class, () -> bookCatalogue.giveBook(isbn));
        }
    }

    @Nested
    class ReturnBooks {

        private final BookCatalogue bookCatalogue = new BookCatalogue();

        @BeforeEach
        void addBooks() {
            bookCatalogue.addBook(hpjp, 2);
            bookCatalogue.addBook(lotr, 9);
        }

        @Test
        void mustReturn_HPJP() {
            bookCatalogue.takeBook(hpjp);
            assertEquals(3, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void mustReturn_LOTR() {
            bookCatalogue.takeBook(lotr);
            assertEquals(10, bookCatalogue.getBookCount(lotr));
        }

        @Test
        void tryReturn_EJ() {
            assertThrows(UnknownBookException.class, () -> bookCatalogue.takeBook(ej));
        }
    }
}
