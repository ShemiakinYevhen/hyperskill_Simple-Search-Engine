package search;

import search.models.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

abstract class SearchingStrategy {

    protected abstract ArrayList<User> findPeople(ArrayList<User> people, Map<String, Set<Integer>> invertedIndex, String searchQuery);
}
