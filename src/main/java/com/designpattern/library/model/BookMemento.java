package com.designpattern.library.model;

import java.util.List;

public record BookMemento(
        String title,
        String author,
        int publicationYear,
        String isbn,
        String publisher,
        List<String> categories,
        List<String> tags,
        int borrowCount,
        boolean available
) {
}
