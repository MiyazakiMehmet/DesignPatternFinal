package com.designpattern.library.search;

import com.designpattern.library.model.Book;

import java.util.Comparator;
import java.util.List;

public class DescendingTitleSortStrategy implements SortStrategy {
    @Override
    public void sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
    }
}
