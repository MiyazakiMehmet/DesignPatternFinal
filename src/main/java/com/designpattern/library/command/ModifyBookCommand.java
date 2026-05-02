package com.designpattern.library.command;

import com.designpattern.library.model.Book;
import com.designpattern.library.model.BookMemento;

import java.util.function.Consumer;

public class ModifyBookCommand implements Command {
    private final Book book;
    private final Consumer<Book> modification;
    private BookMemento beforeSnapshot;

    public ModifyBookCommand(Book book, Consumer<Book> modification) {
        this.book = book;
        this.modification = modification;
    }

    @Override
    public void execute() {
        beforeSnapshot = book.createMemento();
        modification.accept(book);
    }

    @Override
    public void undo() {
        if (beforeSnapshot != null) {
            book.restore(beforeSnapshot);
        }
    }
}
