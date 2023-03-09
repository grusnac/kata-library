package com.github.grusanc.library;

import java.util.Objects;

public record Reader(String firstName, String lastName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(firstName, reader.firstName)
                && Objects.equals(lastName, reader.lastName);
    }

}
