package com.designpattern.library.search;

import com.designpattern.library.model.Book;

import java.util.List;

public interface SortStrategy {
    void sort(List<Book> books);
}
