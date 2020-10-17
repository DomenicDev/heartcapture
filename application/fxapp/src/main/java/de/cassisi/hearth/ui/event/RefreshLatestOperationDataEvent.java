package de.cassisi.hearth.ui.event;

public class RefreshLatestOperationDataEvent {

    private final boolean sortByLatest;
    private final int limit;

    public RefreshLatestOperationDataEvent(boolean sortByLatest, int limit) {
        this.sortByLatest = sortByLatest;
        this.limit = limit;
    }

    public boolean isSortByLatest() {
        return sortByLatest;
    }

    public int getLimit() {
        return limit;
    }
}
