package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public class CategorySearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getCategories().stream()
                .anyMatch(category -> category.equalsIgnoreCase(query.trim()));
    }
}
