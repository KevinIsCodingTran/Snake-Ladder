# Snake-Ladder
Basic Java game of snakes and ladders.

Part I:
 In this part, you need to create a class called LadderAndSnake. A LadderAndSnake object has a
board of 10x10 and a number of players attached to it, which is initialized at the creation time of
the object. The number of players must be between 2 and 4 inclusively. The number of players
must be set through the use of a parameterized constructor of the class. 

 Besides the constructors, and all basic methods in the class, the class must include two methods,
one called flipDice(), which should return a random value between 1 and 6 inclusively. The other
method is called play(), which actually initiate the core engine of the game where the players
start to play the game. The rules of the game are as below:
- Before any of the players starts playing, the order of playing turns must be determined.
For that, each player must throw the dice to obtain the largest possible number. In case of
a tie between any of the players, the process is repeated only between those players. This
process is concluded once the order of playing is determined.
- At this point, the players start playing the game by alternating dice flipping.
- Each dice flip will move a player form square 0 (which you can think about it as outside
the board) with the value of the dice. For example, if a player is at square 0 and the dice
value was 5, then the player moves to square 5.
- If the reached square has a bottom of a ladder, then the player moves up to the square that
has the top of the ladder. For instance, if a player is at square 33, and the flipped dice
value was 3, then the player moves to square 36, which in turn will end moving the
player up to square 44.
- If the reached square has a head of a snake, then the player moves down the square that
has the tail of the snake. For instance, if a player is at square 77, and the flipped dice
value 2, then the player moves to square 79 (which has the tip of the snake’s head), which
in turn end moving the player down to square 19.
 You will have to find a way to record the relation between these particular ladder and
snake squares.
- The game is concluded once any of the players EXACTLY, reaches square 100.
- If a player is close to 100, and the dice value exceeds the maximum possible moves, the
player moves backward with the excessive amount. For instance, if a player is at square
96 and the dice value is 5, then the player moves to 99 (that is 4 moves to 100, then 1
move backward to 99). 
 As a general requirement, you must show/display ALL operations of the game. 

Part II:
 Create a public driver class called PlayLadderAndSnake. In the main() method, you need to prompt the
user to enter the number of players that he/she needs to play the game with. The user must enter a value
between 2 and 4. If the user does not enter a correct value, the program must prompt the user again to
enter a correct value. However, there is a limit of 4 total attempts. If the user enters an incorrect value 4
times, then the program must display a message to this effect and terminates. 
- Of course entering a good value before the number of attempts is exhaused will resut in the game
being payed until one player wins. 
