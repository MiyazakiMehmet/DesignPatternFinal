package com.designpattern.library.model;

import java.util.List;

public class BookFactory {
    public static Book createBook(String title, String author, int publicationYear, String isbn, String publisher, List<String> categories, List<String> tags) {
        return new Book(title, author, publicationYear, isbn, publisher, categories, tags);
    }
}