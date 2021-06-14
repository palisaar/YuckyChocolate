/**
*
* Ritterspiel aka Yucky Chocolate
* last changed: 14.06.21
* @author Lennart Palisaar
*
* */

/*
 * Sources (not for code):
 *
 * understanding the math:
 * https://www.whydomath.org/Reading_Room_Material/ian_stewart/chocolate/chocolate.html
 * https://en.wikipedia.org/wiki/Chomp
 * https://de.wikipedia.org/wiki/Chomp
 *
 * sim to test out scenarios:
 * https://www.geogebra.org/m/udJMn4Am
 *
 * */

import java.util.Scanner;
import java.util.ArrayList;


public class Ritterspiel {

    private static int x; //width of board
    private static int y; //height of board
    private static ArrayList<Integer> XBreak = new ArrayList<Integer>();
    private static ArrayList<Integer> YBreak = new ArrayList<Integer>();
    private static char breakPoint; //saves the breakpoint
    private static boolean playerOne = true; //when not player One -> player Two :)


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static boolean colorAllowed = false;


    //takes the inital user input for board measurements
    public static int[] initialUserInput(){
        int xy[];
        xy = new int[2];

        Scanner sc = new Scanner(System.in);

        System.out.print("How wide should the board be?");
        xy[0] = sc.nextInt();

        System.out.print("How tall should the board be?");
        xy[1] = sc.nextInt();

        System.out.print("Would you like to play in pretty-mode? y/n\n(Causes Problems in non-Unix Consoles)");
        char yn = sc.next().charAt(0);

        if(yn == 'y'){
            colorAllowed = true;
            System.out.println("Pretty-mode is turned on.");
        } else {
            colorAllowed = false;
            System.out.println("Pretty-mode is turned off.");
        }

        //sc.close(); //somehow this crashes my program??

        return xy;
    }

    //returns an int the user inputted
    public static char userInputInt(){
        char i;
        Scanner sc = new Scanner(System.in);
        String player;

        if(playerOne){
            player = "Player One";
        } else {
            player = "Player Two";
        }



        System.out.print("Where do you want to break, " + player + "?");
        i = sc.next().charAt(0);
        //sc.close();

        return i;
    }

    //prints the board. takes the width and height of the board as int - if colorAllowed == true: the board is in color
    //color not supported by cmd or powershell :( (is supported by Unix shell prompts)
    public static void updateBoard(int x, int y){
        XBreak.clear();
        YBreak.clear();

        if(colorAllowed) {
            for (int i = 0; i < y; i++) {
                System.out.print("   ");
                for (int j = 0; j < x; j++) {
                    if (i == 0 && j == 0) {
                        System.out.print(ANSI_BLUE + "█   " + ANSI_RESET);
                    } else {
                        System.out.print(ANSI_YELLOW + "█   " + ANSI_RESET);
                    }
                }
                System.out.print("\n");
                if(i < y-1) {
                    System.out.println(i);
                    YBreak.add(i);
                }
            }
            System.out.print("  ");
            for (int i = 0; i < x - 1; i++) {
                int t = 65 + i;
                System.out.printf("   %c", t);
                XBreak.add(t);
            }
        } else {
            for (int i = 0; i < y; i++) {
                System.out.print("   ");
                for (int j = 0; j < x; j++) {
                    if (i == 0 && j == 0) {
                        System.out.print("X   ");
                    } else {
                        System.out.print("0   ");
                    }
                }
                System.out.print("\n");
                if(i < y-1) {
                    YBreak.add(i);
                }
            }
            System.out.print("  ");
            for (int i = 0; i < x - 1; i++) {
                int t = 65 + i;
                System.out.printf("   %c", t);
                XBreak.add(t);
            }
        }
        System.out.println("");
    }

    //runs a constant game loop
    public static void gameLoop() {
        // while true loop for the endless game loop? game end thru y/n question when someone has won?

        int xy[] = initialUserInput();
        x = xy[0];
        y = xy[1];

        System.out.println("This is your board!");
        updateBoard(x,y);


        while(y != 1 || x !=1){
            breakPoint = userInputInt(); // the point where the chocolate should be broken

            //gets the breakpoint and derives how the table should be changed from that
            if(YBreak.contains(Character.getNumericValue(breakPoint))){
                y = Character.getNumericValue(breakPoint) + 1;
            } else if (XBreak.contains((int)breakPoint)) {
                int temp = (int)breakPoint - 64;
                x = temp;
            } else {
                System.out.println("Nah-ah, not an option.");
            }

            updateBoard(x,y);//prints the table from the changed values
            playerOne = !playerOne;//changes the player to determine who won
        }
        if(playerOne) {
            System.out.println("PlayerOne has to eat the Yucky Chocolate");
        } else {
            System.out.println("PlayerTwo has to eat the Yucky Chocolate");
        }

        //restarts the game loop if the player wants to play again.
        System.out.println("Would you like to play again? y/n");
        Scanner sc = new Scanner(System.in);
        char yn = sc.next().charAt(0);
        if(yn == 'y'){
            gameLoop();
        }

    }

    //prints a fancy title
    public static void printTitle() {
        // made with http://www.kammerl.de/ascii/AsciiSignature.php
        System.out.println("-----------------------------------------------------------");
        System.out.println(".  .         .           ,--. .               .      .     ");
        System.out.println("|  | . . ,-. | , . .    | `-' |-. ,-. ,-. ,-. |  ,-. |- ,-.");
        System.out.println("|  | | | |   |<  | |    |   . | | | | |   | | |  ,-| |  |-'");
        System.out.println("`--| `-^ `-' ' ` `-|    `--'  ' ' `-' `-' `-' `' `-^ `' `-'");
        System.out.println(".- |              /|                                       ");
        System.out.println("`--'             `-'                                       ");
        System.out.println("");
        System.out.println("");
        System.out.println("by Lennart Palisaar");
        System.out.println("-----------------------------------------------------------");
        System.out.println("           Win by being the one who reduces the");
        System.out.println("            board to just the top lef corner.");
        System.out.println("         Select where you want to break off the the");
        System.out.println("        chocolate by inputting that number or letter.");
        System.out.println("                        gl,hf!");
        System.out.println("-----------------------------------------------------------");
    }

    public static void main(String[] args) {
        printTitle();
        gameLoop();

    }

}