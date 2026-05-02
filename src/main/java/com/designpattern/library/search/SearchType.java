package com.designpattern.library.search;

public enum SearchType {
    TITLE("title", new TitleSearchStrategy()),
    AUTHOR("author", new AuthorSearchStrategy()),
    ISBN("isbn", new IsbnSearchStrategy()),
    CATEGORY("category", new CategorySearchStrategy()),
    TAG("tag", new TagSearchStrategy());

    private final String label;
    private final SearchStrategy strategy;

    SearchType(String label, SearchStrategy strategy) {
        this.label = label;
        this.strategy = strategy;
    }

    public SearchStrategy getStrategy() {
        return strategy;
    }

    public static SearchType fromLabel(String label) {
        for (SearchType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        return TITLE;
    }
}
