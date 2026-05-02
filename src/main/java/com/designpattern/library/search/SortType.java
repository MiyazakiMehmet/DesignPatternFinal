package com.designpattern.library.search;

public enum SortType {
    ASC("asc", new AscendingTitleSortStrategy()),
    DESC("desc", new DescendingTitleSortStrategy());

    private final String label;
    private final SortStrategy strategy;

    SortType(String label, SortStrategy strategy) {
        this.label = label;
        this.strategy = strategy;
    }

    public SortStrategy getStrategy() {
        return strategy;
    }

    public static SortType fromLabel(String label) {
        for (SortType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        return ASC;
    }
}
