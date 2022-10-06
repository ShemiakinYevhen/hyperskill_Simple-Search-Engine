package search;

public class SearchContext {

    private SearchingStrategy strategy;

    public SearchContext(SearchingStrategy strategy) {
        setStrategy(strategy);
    }

    public SearchingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SearchingStrategy strategy) {
        this.strategy = strategy;
    }
}
