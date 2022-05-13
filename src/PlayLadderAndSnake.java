// -----------------------------------------------------
// Assignment #1
// COMP249
// Question: Snakes and Ladders Game/Part 2
// Written by: Kevin Tran - 40209451
// Due Date: Feb 07 2022
// -----------------------------------------------------

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program contains the driver class which allows the players to enter the number of players and a letter
 * they can recognize on the board. Then, the program chooses the order of the players that will play first. When
 * the players are ready, they can start the game by pressing enter.
 *
 * @author Kevin Tran
 */
public class PlayLadderAndSnake {
    /**
     * Method returns a boolean if exist a duplicate roll
     *
     * @param rollMap
     * @return
     */
    private static boolean isDup(Map<Integer, List<String>> rollMap) { // Check if there is a duplicate dice roll
        for (int i = 1; i <= rollMap.size(); i++) {
            if (rollMap.get(i).size() >= 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Main method with welcome/closing message, asks for the number of players and a name (single letter), as well as verifies if they entered correctly.
     * The method will then generate the order of players that will be playing and initialize the game of LadderAndSnake. Once the players are ready, they can
     * press 'enter' to start playing. Every round the user has to press 'enter' to confirm that they are still playing, until there is a winner.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int numbPlayers;
        String[] namesArray;

        // Welcome message
        System.out.println("Welcome to Kevin Tran's game of Snakes 'n' Ladders!\n");

        // Prompt user to enter information
        boolean isLetter;
        do { // Check if user entered the correct information
            boolean notNumb;
            int attempts = 0;
            do {
                System.out.print("How many players will there be (2-4)? "); // Number of players that will be playing
                numbPlayers = kb.nextInt();
                kb.nextLine();
                if (numbPlayers < 2 || numbPlayers > 4) { // Verify input
                    notNumb = true;
                    attempts++;
                    System.out.println("Please enter a number between 2-4!");
                } else {
                    notNumb = false;
                }
            } while (notNumb && attempts < 4);
            // If exceeds number of attempts
            if (attempts >= 4) {
                System.out.println("Too many attempts!");
                System.exit(0);
            }
            // Check if user entered a letter correctly
            isLetter = true;
            do {
                System.out.print("Enter a letter separated by '/'"
                        + "\n(ex: A/b/C/d): "); // This will be displayed as a player on the board
                String names = kb.nextLine();
                namesArray = names.split("/");

                for (int i = 0; i < namesArray.length; i++) {
                    String regex = "^[A-z]$";
                    Pattern symbolPat = Pattern.compile(regex);
                    Matcher matcher = symbolPat.matcher(namesArray[i]);
                    if (!matcher.find()) {
                        System.out.println("Not a letter!\n");
                        isLetter = false;
                        break;
                    } else {
                        isLetter = true;
                    }
                }
                if (namesArray.length != numbPlayers) {
                    System.out.println("Number of players don't match names!\n"); // Wrong input
                }
            } while (!isLetter);
        } while (namesArray.length != numbPlayers);

        // Deciding the order of players
        System.out.println("Now deciding who will start playing:");
        Map<Integer, List<String>> rollMap = new LinkedHashMap<Integer, List<String>>();

        // Initialize rollMap for deciding sorting
        for (int i = 1; i <= 6; i++) {
            rollMap.put(i, new ArrayList<>());
        }

        // First dice roll
        for (int i = 0; i < numbPlayers; i++) {
            int roll = LadderAndSnake.flipDice();
            rollMap.get(roll).add(namesArray[i]);
            System.out.println(namesArray[i] + " got a dice roll of " + roll);
        }

        // Creating order of players and dealing with duplicates
        String namesDup = "";
        List<String> tempNames = new ArrayList<>();
        do {
            for (int i = rollMap.size(); i > 0; i--) {
                if (rollMap.get(i).size() == 1) { // No duplicates for this dice roll
                    tempNames.add(rollMap.get(i).get(0));
                    rollMap.get(i).clear();
                }
                else if (rollMap.get(i).size() > 1) { // There's a duplicate
                    String[] temp = new String[rollMap.get(i).size()];
                    for (int k = 0; k < rollMap.get(i).size(); k++) {
                        namesDup += rollMap.get(i).get(k) + ", ";
                        temp[k] = rollMap.get(i).get(k);
                    }
                    if (!namesDup.equals("")) {
                        System.out.println("\nThere's a tie between: " + namesDup + "attempt to break tie: ");
                    }
                    int k = 0;
                    for (int j = 0; j < temp.length; j++) { // Rolling again in case of duplicate and assigning names to new dice roll
                        int roll = LadderAndSnake.flipDice();
                        System.out.println(temp[j] + " rolled the dice and got a " + roll);
                        if (roll != i) {
                            String name = temp[j];
                            rollMap.get(roll).add(name);
                            rollMap.get(i).remove(k);
                        } else {
                            k++;
                        }
                    }
                    namesDup = "";
                    break;
                } else {
                    continue;
                }
            }
        } while (isDup(rollMap)); // Will exit loop when there are no duplicates anymore

        // Storing names in array
        for (int i = 0; i < numbPlayers; i++) {
            namesArray[i] = tempNames.get(i);
        }

        // Announcing the order of players
        System.out.print("The order of players is: ");
        for(int i = 0; i < namesArray.length; i++) {
            System.out.print(namesArray[i] + ", ");
        }
        System.out.println("");

        // Creating an object "game" with the number of players and the order array
        LadderAndSnake game = new LadderAndSnake(numbPlayers, namesArray);

        // Play game while players press "enter"
        do {
            System.out.println("\nPress 'enter' to play!");
            kb.nextLine();
            game.play();
            System.out.println(game.toString());
        } while (game.winner().equals(""));
        System.out.println(game.winner() + " is the winner!");

        // Closing message
        System.out.println("\nThe game has ended!");

    }
}
