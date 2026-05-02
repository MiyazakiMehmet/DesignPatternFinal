package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public class TagSearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getTags().stream()
                .anyMatch(tag -> tag.equalsIgnoreCase(query.trim()));
    }
}
