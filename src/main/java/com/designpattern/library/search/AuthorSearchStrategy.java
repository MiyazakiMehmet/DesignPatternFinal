package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public class AuthorSearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getAuthor().toLowerCase().contains(query.toLowerCase());
    }
}
