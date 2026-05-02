package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public class IsbnSearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getIsbn().equalsIgnoreCase(query.trim());
    }
}
