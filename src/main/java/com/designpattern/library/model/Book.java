package com.designpattern.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private String publisher;
    private List<String> categories;
    private List<String> tags;
    private int borrowCount;
    private boolean available;

    public Book(String title, String author, int publicationYear, String isbn, String publisher,
                List<String> categories, List<String> tags) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.publisher = publisher;
        this.categories = new ArrayList<>(categories);
        this.tags = new ArrayList<>(tags);
        this.borrowCount = 0;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void setCategories(List<String> categories) {
        this.categories = new ArrayList<>(categories);
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        if (!available) {
            return;
        }
        this.available = false;
        borrowCount++;
    }

    public void returned() {
        this.available = true;
    }

    public BookMemento createMemento() {
        return new BookMemento(title, author, publicationYear, isbn, publisher, new ArrayList<>(categories), new ArrayList<>(tags), borrowCount, available);
    }

    public void restore(BookMemento memento) {
        this.title = memento.title();
        this.author = memento.author();
        this.publicationYear = memento.publicationYear();
        this.isbn = memento.isbn();
        this.publisher = memento.publisher();
        this.categories = new ArrayList<>(memento.categories());
        this.tags = new ArrayList<>(memento.tags());
        this.borrowCount = memento.borrowCount();
        this.available = memento.available();
    }

    public String detailedString() {
        return String.format(
                "Title: %s%nAuthor: %s%nPublication Year: %d%nISBN: %s%nPublisher: %s%nCategories: %s%nTags: %s%nAvailability: %s%nBorrow Count: %d",
                title, author, publicationYear, isbn, publisher, categories, tags, available ? "Available" : "Borrowed", borrowCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
