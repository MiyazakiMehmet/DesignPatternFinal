package com.designpattern.library.search;

import com.designpattern.library.model.Book;

public interface SearchStrategy {
    boolean matches(Book book, String query);
}
