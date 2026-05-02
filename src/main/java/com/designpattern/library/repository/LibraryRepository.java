package com.designpattern.library.repository;

import com.designpattern.library.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LibraryRepository {
    private final List<Book> books = new ArrayList<>();

    public void save(Book book) {
        books.add(book);
    }

    public List<Book> findAll() {
        return Collections.unmodifiableList(books);
    }

    public Optional<Book> findById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }
}
