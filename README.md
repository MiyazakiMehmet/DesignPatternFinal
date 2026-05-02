# Library Management System

This repository contains a Java console application implementing a library management system with support for book creation, modification, search, borrowing, and return.

## Build

Run from the repository root:

```bash
mvn clean package
```

## Run

```bash
java -jar target/library-management-1.0-SNAPSHOT.jar
```

## Design Patterns Used

- Factory Pattern: `BookFactory` is used to create `Book` objects during creation.
- Command Pattern: `ModifyBookCommand` with `CommandProcessor` supports runtime modification execution and undo behavior.
- Strategy Pattern: `SearchStrategy` and `SortStrategy` allow search and sort behavior to vary without changing core classes.

## Notes

The existing C# solution files are left intact. The Java Maven project is located in the repository root and produces an executable JAR.
