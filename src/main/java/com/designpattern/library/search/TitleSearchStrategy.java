package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getTitle().toLowerCase().contains(query.toLowerCase());
    }
}
