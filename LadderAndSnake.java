// -----------------------------------------------------
// Assignment #1
// COMP249
// Question: Snakes and Ladders Game/Part 2
// Written by: Kevin Tran - 40209451
// Due Date: Feb 07 2022
// -----------------------------------------------------

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class contains the main parts of the game: initialize the game, method to start playing, method to print out
 * the board, method to flip the dice and to stop when there's a winner.
 *
 * @author Kevin Tran
 */
public class LadderAndSnake {
    // Instance variables and constants
    private int numbPlayers;
    private int[][] board;

    private int[][] snakes;
    private int[][] ladders;

    private static final int rows = 10;
    private static final int cols = 10;
    private static final int numSnakes = 8;
    private static final int numLadders = 9;

    Map<String, Integer> playersPosition;

    /**
     * The default constructor that initializes the instance variables to their null values
     */
    public LadderAndSnake() {
        this.numbPlayers = 0;
        this.board = null;
    }

    /**
     * A copy constructor to avoid privacy leaks
     *
     * @param game
     */
    public LadderAndSnake(LadderAndSnake game) {
        this.numbPlayers = game.numbPlayers;
        this.board = game.board;
    }

    /**
     * A constructor with two parameters to initialize an object of LadderAndSnake.
     * This method initializes the player at position zero and creates the board.
     *
     * @param n
     * @param namesArray
     */
    public LadderAndSnake(int n, String[] namesArray) { // Game constructor
        this.numbPlayers = n;

        // Initialize players positions
        this.playersPosition = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < this.numbPlayers; i++) {
            this.playersPosition.put(namesArray[i], 0);
        }

        // Initializing the game board
        board = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                this.board[row][col] = (row * rows) + col + 1;
            }
        }
        // Set Snakes and Ladders
        setSnakes();
        setLadders();
    }

    /**
     * Accessor for numbPlayers
     *
     * @return numbPlayers
     */
    public int getNumbPlayers() {
        return this.numbPlayers;
    }

    /**
     * Accessor for board
     *
     * @return board
     */
    public  int[][] getBoard() {
        return this.board;
    }

    /**
     * Mutator for numbPlayers
     *
     * @param n
     */
    public void setNumbPlayers(int n) {
        this.numbPlayers = n;
    }

    /**
     * Mutator for board
     *
     * @param otherBoard
     */
    public void setBoard(int[][] otherBoard) {
        this.board = otherBoard;
    }

    /**
     * This is the method that will allow the players to advance in the game. It will check when a player lands on a snake or a ladder, when the player exceeds position 100
     * and when a player lands on the winning square (100). Finally, it updates the players position.
     */
    public void play() {
        for (String name : playersPosition.keySet()) {
            int position = playersPosition.get(name);
            position += flipDice(); // Adding to the previous position

            if (position > 100) { // If the dice roll makes the player exceed the 'Winner' square, the player goes back the difference
                playersPosition.put(name, (100 - (position - 100)));
            }
            else if (position == 100) { // If the player lands ont the 'Winner' square
                playersPosition.put(name, 100);
                System.out.println(name + " is the winner!");
                return;
            } else {
                // Check if lands on snake head
                for (int j = 0; j < numSnakes; j++) {
                    if (snakes[j][0] == position) {
                        position = snakes[j][1];
                        playersPosition.put(name, position);

                        System.out.println("*** Oh no, a snake! " + name + " goes from " + snakes[j][0] + " to " + snakes[j][1] + " ***");
                    }
                }
                // Check if lands on bottom of ladder
                for (int j = 0; j < numLadders; j++) {
                    if (ladders[j][0] == position) {
                        position = ladders[j][1];
                        playersPosition.put(name, position);

                        System.out.println("*** Yay a ladder! " + name + " goes from " + ladders[j][0] + " to " + ladders[j][1] + " ***");
                    }
                }
                playersPosition.put(name, position); // Giving a new position to the player that rolled
                System.out.println(name + " is now at position " + position);
            }

        }

    }

    /**
     * If there's a player that lands on the winning square (100), this method will return their name.
     *
     * @return name of winner
     */
    public String winner() {
        for (String name : this.playersPosition.keySet()) {
            if (this.playersPosition.get(name) == 100) {
                return name;
            }
        }
        return "";
    }

    /**
     * Prints out the game board as a table:
     * Printing board from top to bottom
     * "Even": (1-10, 21-30, ..., 81-90) printed left to right
     * "Odd": (11-20, 31-40, ..., 91-100) printed right to left
     *
     * @return string board
     */
    public String toString() {
        String s = "";
        boolean oddRow = true;

        for (int row = rows - 1; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
                if (oddRow) { // (Line: 1, 3, 5, 7, 9)
                    String player = "";
                    boolean occupied = false;
                    for (String name : playersPosition.keySet()) { // If player occupies the current square, set a flag
                        if (playersPosition.get(name) == board[row][cols - 1 - col]){
                            occupied = true;
                            player += name + "";
                        }
                    }
                    if (occupied) { // Player's name will be displayed instead of the square number
                        player += "\t";
                        s += player;
                    } else { // Else print the square's number
                        s += (board[row][cols - 1 - col] + "\t");
                    }

                } else { // evenRow (Line: 0, 2, 4, 6, 8)
                    boolean occupied = false;
                    String player = "";
                    for (String name : playersPosition.keySet()) { // If player occupies the current square, set a flag
                        if (playersPosition.get(name) == board[row][col]){
                            occupied = true;
                            player += name + "";
                        }
                    }
                    if (occupied) { // Player's name will be displayed instead of the square number
                        player += "\t";
                        s += player;
                    } else { // Else print the square's number
                        s += (board[row][col] + "\t");
                    }
                }
            }
            oddRow = !oddRow; // Switch oddRow flag
            s += "\n";
        }
        s += "\n";

        return "\n" + s + "\n--------------------------------------------------";
    }

    /**
     * Returns a random number between 1-6 inclusively.
     *
     * @return dice roll
     */
    public static int flipDice() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Setting up the snakes, it's starting and end points
     */
    private void setSnakes() {
        snakes = new int[numSnakes][2];

        snakes[0][0] = 16;
        snakes[0][1] = 6;
        snakes[1][0] = 48;
        snakes[1][1] = 30;
        snakes[2][0] = 62;
        snakes[2][1] = 19;
        snakes[3][0] = 63;
        snakes[3][1] = 60;
        snakes[4][0] = 93;
        snakes[4][1] = 68;
        snakes[5][0] = 95;
        snakes[5][1] = 24;
        snakes[6][0] = 97;
        snakes[6][1] = 76;
        snakes[7][0] = 98;
        snakes[7][1] = 78;
    }

    /**
     * Setting up the ladders, it's starting and end points
     */
    private void setLadders() {
        ladders = new int[numLadders][2];

        ladders[0][0] = 1;
        ladders[0][1] = 38;
        ladders[1][0] = 4;
        ladders[1][1] = 14;
        ladders[2][0] = 9;
        ladders[2][1] = 31;
        ladders[3][0] = 21;
        ladders[3][1] = 42;
        ladders[4][0] = 28;
        ladders[4][1] = 84;
        ladders[5][0] = 36;
        ladders[5][1] = 44;
        ladders[6][0] = 51;
        ladders[6][1] = 67;
        ladders[7][0] = 71;
        ladders[7][1] = 91;
        ladders[8][0] = 80;
        ladders[8][1] = 100;
    }
}
