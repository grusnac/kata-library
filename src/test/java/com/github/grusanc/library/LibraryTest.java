package com.github.grusanc.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LibraryTest {
    private final Book hpjp = new Book("973022823X", "High-Performance Java Persistence");
    private final Book lotr = new Book("9780544003415", "The Lord Of The Rings");
    private final Book ej = new Book("0134685997", "Effective Java");

    private final BookCatalogue bookCatalogue = new BookCatalogue(
            Map.of(
                    hpjp, 2,
                    lotr, 4
            )
    );

    private final Reader reader = new Reader("John", "Doe");


    @Nested
    class AddBooks {

        private final Library library = new Library(bookCatalogue);

        @Test
        void mustAddOneBook() {
            library.addBook(hpjp);
            assertEquals(1, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void mustAddTreeBooks() {
            library.addBook(hpjp, 3);
            assertEquals(3, bookCatalogue.getBookCount(hpjp));
        }
    }

    @Nested
    class BarrowBooks {

        private final Library library = new Library(bookCatalogue);

        @BeforeEach
        void addBooks() {
            library.addBook(hpjp, 3);
            library.addBook(lotr, 10);
        }

        @Test
        void mustBorrowOneBook_HPJP() {
            library.lendBook(hpjp.isbn(), reader);
            assertEquals(2, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void mustBorrowTwoBooks_HPJP_LOTR() {
            library.lendBook(hpjp.isbn(), reader);
            library.lendBook(lotr.isbn(), reader);
            assertEquals(2, bookCatalogue.getBookCount(hpjp));
            assertEquals(9, bookCatalogue.getBookCount(lotr));
        }

        @Test
        void tryBarrow_EJ() {
            final String isbn = ej.isbn();
            assertThrows(BookNotFoundException.class, () -> library.lendBook(isbn, reader));
        }
        @Test
        void tryBarrowTwice_HPJP() {
            library.lendBook(hpjp.isbn(), reader);
            final String isbn = hpjp.isbn();
            assertThrows(BookAlreadyLentException.class, () -> library.lendBook(isbn, reader));
        }
    }

    @Nested
    class ReturnBooks {

        private final Library library = new Library(bookCatalogue);

        @BeforeEach
        void addBooks() {
            library.addBook(hpjp, 3);
            library.addBook(lotr, 10);
            library.lendBook(hpjp.isbn(), reader);
        }

        @Test
        void mustReturn_HPJP() {
            library.returnBook(hpjp, reader);
            assertEquals(3, bookCatalogue.getBookCount(hpjp));
        }

        @Test
        void tryReturn_LOTR() {
            assertThrows(UnknownLentBookException.class, () -> library.returnBook(lotr, reader));
        }
    }

    @Nested
    class PrintBorrowedBooks {
        private final Library library = new Library(bookCatalogue);

        @BeforeEach
        void addBooks() {
            library.addBook(hpjp, 2);
            library.addBook(lotr, 9);
        }

        @Test
        void mustPrintOneBorrowedBooks() {
            library.lendBook(hpjp.isbn(), reader);
            library.showBorrowedBooks(reader);
        }

        @Test
        void mustPrintZeroBorrowedBooks() {
            library.showBorrowedBooks(reader);
        }
    }
}
