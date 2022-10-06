package search;

import search.models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static Map<String, Set<Integer>> invertedIndexBox = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<User> users = getInitialInfo(args[1]);

        while(true) {
            System.out.println("\n=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit");
            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 0:
                    System.out.println("\nBye!");
                    return;
                case 1:
                    searchForPeople(users, scanner);
                    break;
                case 2:
                    printPeople(users);
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    private static ArrayList<User> getInitialInfo(String nameOfFile) {
        File file = new File(nameOfFile);
        ArrayList<User> users = new ArrayList<>();

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("No file found with given name!");
        }

        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(" ");
                User tempUser = new User();

                if (userData.length == 1) {
                    tempUser = new User().setFirstName(userData[0]).setLastName("").setEmail("");
                } else if (userData.length == 2) {
                    tempUser = new User().setFirstName(userData[0]).setLastName(userData[1]).setEmail("");
                } else if (userData.length == 3) {
                    tempUser = new User().setFirstName(userData[0]).setLastName(userData[1]).setEmail(userData[2]);
                }

                users.add(tempUser);

                for (String pieceOfData : userData) {
                    if (invertedIndexBox.containsKey(pieceOfData.toLowerCase())) {
                        Set<Integer> tempSetOfMatchingLinesIndexes = new HashSet<>(invertedIndexBox.get(pieceOfData.toLowerCase()));
                        tempSetOfMatchingLinesIndexes.add(users.indexOf(tempUser));
                        invertedIndexBox.put(pieceOfData.toLowerCase(), tempSetOfMatchingLinesIndexes);
                    } else {
                        invertedIndexBox.put(pieceOfData.toLowerCase(), Set.of(users.indexOf(tempUser)));
                    }
                }
            }
        } else {
            System.out.println("No users found in given file!");
        }

        return users;
    }

    private static void printPeople(ArrayList<User> people) {
        System.out.println("\n=== List of people ===");
        for (User user : people) {
            System.out.println(user.toString().trim());
        }
    }

    private static void searchForPeople(ArrayList<User> people, Scanner scanner) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE (or leave blank for ALL)");

        String strategy = scanner.nextLine();

        SearchContext context;

        switch (strategy.toUpperCase()) {
            case "ANY":
                context = new SearchContext(new SearchAnyStrategy());
                break;
            case "NONE":
                context = new SearchContext(new SearchNoneStrategy());
                break;
            default:
                context = new SearchContext(new SearchAllStrategy());
        }

        System.out.println("\nEnter a name or email to search all suitable people");

        String searchQuery = scanner.nextLine().toLowerCase();

        ArrayList<User> foundPeople = context.getStrategy().findPeople(people, invertedIndexBox, searchQuery);

        if (foundPeople == null || foundPeople.size() ==0) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(foundPeople.size() + " person(s) found:");
            for (User user : foundPeople) {
                System.out.println(user.toString().trim());
            }
        }
    }
}
