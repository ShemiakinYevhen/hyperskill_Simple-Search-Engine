package search;

import search.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SearchAnyStrategy extends SearchingStrategy {

    @Override
    public ArrayList<User> findPeople(ArrayList<User> people, Map<String, Set<Integer>> invertedIndex, String searchQuery) {
        String[] searchQueryWords = searchQuery.split(" ");
        Set<User> foundPeople = new HashSet<>();

        for (String searchQueryWord : searchQueryWords) {
            Set<Integer> matchingLinesIndexes = invertedIndex.get(searchQueryWord);
            if (matchingLinesIndexes != null && matchingLinesIndexes.size() > 0) {
                for (int index : matchingLinesIndexes) {
                    foundPeople.add(people.get(index));
                }
            }
        }

        return new ArrayList<>(foundPeople);
    }
}
