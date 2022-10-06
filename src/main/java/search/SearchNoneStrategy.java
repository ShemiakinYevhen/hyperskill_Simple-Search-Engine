package search;

import search.models.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SearchNoneStrategy extends SearchingStrategy{

    @Override
    public ArrayList<User> findPeople(ArrayList<User> people, Map<String, Set<Integer>> invertedIndex, String searchQuery) {
        String[] searchQueryWords = searchQuery.split(" ");
        ArrayList<User> foundPeople = new ArrayList<>();
        boolean matchingFlag = true;

        for (User user : people) {
            for (String searchQueryWord : searchQueryWords) {
                if (user.getEmail().toLowerCase().equals(searchQueryWord) ||
                        user.getFirstName().toLowerCase().equals(searchQueryWord) ||
                        user.getLastName().toLowerCase().equals(searchQueryWord)) {
                    matchingFlag = false;
                }
            }

            if (matchingFlag) {
                foundPeople.add(user);
            }

            matchingFlag = true;
        }

        return foundPeople;
    }
}
