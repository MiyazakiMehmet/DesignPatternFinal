package com.designpattern.library;

import com.designpattern.library.command.CommandProcessor;
import com.designpattern.library.model.Book;
import com.designpattern.library.model.BookFactory;
import com.designpattern.library.search.SearchStrategy;
import com.designpattern.library.search.SearchType;
import com.designpattern.library.search.SortStrategy;
import com.designpattern.library.search.SortType;
import com.designpattern.library.service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static final LibraryService libraryService = new LibraryService();
    private static final CommandProcessor commandProcessor = new CommandProcessor();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printIntroduction();
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createBook();
                case "2" -> searchBook();
                case "3" -> borrowBook();
                case "4" -> modifyBook();
                case "0" -> exitApplication();
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printIntroduction() {
        System.out.println("=== Library Management System ===");
        System.out.println("This console application uses Factory, Command, and Strategy patterns.");
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("Main Menu:");
        System.out.println("1. Create Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Modify Book");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private static void createBook() {
        System.out.println("\n== Create New Book ==");
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Publication Year: ");
        int year = readInt("Publication Year");
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Publisher: ");
        String publisher = scanner.nextLine().trim();

        List<String> categories = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        addCategories(categories);
        addTags(tags);

        System.out.println("\nBook Details:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("ISBN: " + isbn);
        System.out.println("Publisher: " + publisher);
        System.out.println("Categories: " + categories);
        System.out.println("Tags: " + tags);

        System.out.println("\nOptions:");
        System.out.println("1. Save Book");
        System.out.println("2. Cancel");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine().trim();
        if ("1".equals(choice)) {
            Book book = BookFactory.createBook(title, author, year, isbn, publisher, categories, tags);
            libraryService.addBook(book);
            System.out.println("Book saved: " + book.getTitle());
        } else if ("2".equals(choice)) {
            System.out.println("Book creation cancelled.");
        } else {
            System.out.println("Invalid choice. Book creation cancelled.");
        }
    }

    private static void addCategories(List<String> categories) {
        System.out.println("Add up to 3 categories. Press enter without typing to finish.");
        for (int i = 1; i <= 3; i++) {
            System.out.print("Category " + i + ": ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) {
                break;
            }
            categories.add(category);
        }
    }

    private static void addTags(List<String> tags) {
        System.out.println("Add up to 3 tags. Press enter without typing to finish.");
        for (int i = 1; i <= 3; i++) {
            System.out.print("Tag " + i + ": ");
            String tag = scanner.nextLine().trim();
            if (tag.isEmpty()) {
                break;
            }
            tags.add(tag);
        }
    }

    private static void searchBook() {
        System.out.println("\n== Search Books ==");
        System.out.print("Search by (title/author/isbn/category/tag): ");
        String criteria = scanner.nextLine().trim();
        SearchStrategy strategy = SearchType.fromLabel(criteria).getStrategy();
        System.out.print("Enter search keyword: ");
        String query = scanner.nextLine().trim();
        List<Book> results = libraryService.searchBooks(strategy, query);

        if (results.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.print("Sort by title (asc/desc): ");
        String sortOrder = scanner.nextLine().trim();
        SortStrategy sortStrategy = SortType.fromLabel(sortOrder).getStrategy();
        sortStrategy.sort(results);

        printBooks(results);
        viewBookDetails(results);
    }

    private static void printBooks(List<Book> books) {
        System.out.println("\nSearch results:");
        for (int i = 0; i < books.size(); i++) {
            System.out.printf("%d. %s by %s [%s]%n", i + 1, books.get(i).getTitle(), books.get(i).getAuthor(), books.get(i).getIsbn());
        }
    }

    private static void viewBookDetails(List<Book> results) {
        System.out.print("Select a book number to view details, or press Enter to return: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < results.size()) {
                Book book = results.get(index);
                System.out.println(book.detailedString());
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    private static void borrowBook() {
        System.out.println("\n== Borrow/Return Book ==");
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }
        printBooks(books);
        System.out.print("Select a book number: ");
        String input = scanner.nextLine().trim();

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < books.size()) {
                Book book = books.get(index);
                System.out.println("Selected book: " + book.getTitle() + " [" + (book.isAvailable() ? "Available" : "Borrowed") + "]");
                System.out.println("\nOptions:");
                System.out.println("1. Borrow Book");
                System.out.println("2. Return Book");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                String choice = scanner.nextLine().trim();
                if ("1".equals(choice)) {
                    boolean result = libraryService.borrowBook(book.getId());
                    System.out.println(result ? "Book borrowed successfully." : "Book is currently unavailable.");
                } else if ("2".equals(choice)) {
                    boolean result = libraryService.returnBook(book.getId());
                    System.out.println(result ? "Book returned successfully." : "Book was not borrowed.");
                } else if ("0".equals(choice)) {
                    return;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    private static void modifyBook() {
        System.out.println("\n== Modify Book ==");
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }
        printBooks(books);
        System.out.print("Select a book number: ");
        String input = scanner.nextLine().trim();
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < books.size()) {
                Book book = books.get(index);
                System.out.println("Selected book: " + book.getTitle());
                System.out.println("\nOptions:");
                System.out.println("1. Modify Book");
                System.out.println("2. Undo Last Modification");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                String choice = scanner.nextLine().trim();
                if ("1".equals(choice)) {
                    applyBookModifications(book.getId());
                } else if ("2".equals(choice)) {
                    undoLastModification();
                } else if ("0".equals(choice)) {
                    return;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    private static void applyBookModifications(String bookId) {
        System.out.println("Enter new values or press Enter to keep existing values.");
        Book book = libraryService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("New author [" + book.getAuthor() + "]: ");
        String author = scanner.nextLine().trim();
        System.out.print("New publication year [" + book.getPublicationYear() + "]: ");
        String yearInput = scanner.nextLine().trim();
        System.out.print("New ISBN [" + book.getIsbn() + "]: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("New publisher [" + book.getPublisher() + "]: ");
        String publisher = scanner.nextLine().trim();

        List<String> categories = libraryService.promptList(scanner, "category", book.getCategories());
        List<String> tags = libraryService.promptList(scanner, "tag", book.getTags());

        libraryService.modifyBook(bookId, bookToModify -> {
            if (!author.isEmpty()) {
                bookToModify.setAuthor(author);
            }
            if (!yearInput.isEmpty()) {
                int year = Integer.parseInt(yearInput);
                bookToModify.setPublicationYear(year);
            }
            if (!isbn.isEmpty()) {
                bookToModify.setIsbn(isbn);
            }
            if (!publisher.isEmpty()) {
                bookToModify.setPublisher(publisher);
            }
            if (!categories.isEmpty()) {
                bookToModify.setCategories(categories);
            }
            if (!tags.isEmpty()) {
                bookToModify.setTags(tags);
            }
        });

        System.out.println("Book modification saved.");
    }

    private static void undoLastModification() {
        System.out.println("\n== Undo Last Modification ==");
        boolean undone = libraryService.undoLastModification();
        System.out.println(undone ? "Most recent modification has been undone." : "No modification to undo.");
    }

    private static int readInt(String label) {
        while (true) {
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.print("Invalid " + label + ". Enter a numeric value: ");
            }
        }
    }

    private static void exitApplication() {
        System.out.println("Exiting the library system. Goodbye!");
        scanner.close();
        System.exit(0);
    }
}
