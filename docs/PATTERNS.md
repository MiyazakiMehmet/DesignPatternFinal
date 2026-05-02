# Design Patterns Used

## Factory Pattern
- Implemented by `com.designpattern.library.model.BookFactory`.
- Used to create `Book` objects with required fields and optional categories/tags.
- Centralizes book creation logic in a static factory method.

## Command Pattern
- Implemented by `com.designpattern.library.command.Command`, `ModifyBookCommand`, and `CommandProcessor`.
- Supports book modifications and undo of the most recent change.
- Encapsulates each modification as a command object.

## Strategy Pattern
- Implemented by `com.designpattern.library.search.SearchStrategy` and `SortStrategy`.
- Search types (`title`, `author`, `isbn`, `category`, `tag`) are handled by separate strategies.
- Sorting is handled by ascending/descending title strategies.
