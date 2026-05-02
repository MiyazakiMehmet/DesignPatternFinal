package com.designpattern.library.service;

import com.designpattern.library.command.CommandProcessor;
import com.designpattern.library.command.ModifyBookCommand;
import com.designpattern.library.model.Book;
import com.designpattern.library.repository.LibraryRepository;
import com.designpattern.library.search.SearchStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LibraryService {
    private final LibraryRepository repository = new LibraryRepository();
    private final CommandProcessor commandProcessor = new CommandProcessor();

    public void addBook(Book book) {
        repository.save(book);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book findBookById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Book> searchBooks(SearchStrategy strategy, String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        return repository.findAll().stream()
                .filter(book -> strategy.matches(book, query))
                .collect(Collectors.toList());
    }

    public boolean borrowBook(String id) {
        return repository.findById(id).map(book -> {
            if (book.isAvailable()) {
                book.borrow();
                return true;
            }
            return false;
        }).orElse(false);
    }

    public boolean returnBook(String id) {
        return repository.findById(id).map(book -> {
            if (!book.isAvailable()) {
                book.returned();
                return true;
            }
            return false;
        }).orElse(false);
    }

    public void modifyBook(String bookId, Consumer<Book> changes) {
        repository.findById(bookId).ifPresent(book -> {
            ModifyBookCommand command = new ModifyBookCommand(book, changes);
            commandProcessor.execute(command);
        });
    }

    public boolean undoLastModification() {
        return commandProcessor.undoLast();
    }

    public List<String> promptList(java.util.Scanner scanner, String label, List<String> current) {
        System.out.println("Current " + label + "s: " + current);
        System.out.println("Enter up to 3 new " + label + "s. Press Enter to keep existing values.");
        List<String> values = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            System.out.print(label + " " + i + ": ");
            String entry = scanner.nextLine().trim();
            if (entry.isEmpty()) {
                break;
            }
            values.add(entry);
        }
        return values.isEmpty() ? current : values;
    }
}
