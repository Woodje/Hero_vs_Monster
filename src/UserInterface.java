package com.company;

import java.util.Scanner;

/**
 * UserInterface.java - Used for displaying the game and retrieving inputs.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 02/02-2015.
 */
public class UserInterface {

    public enum menu { FIRST, START, SHOWMAP, SELECTMAP, MOVEMENT };

    /**
     * Prints the provided string to the screen.
     * @param outputString - This is a string that will be printed to the screen.
     */
    public void drawToScreen(String outputString) {

        for (int i = 0; i < 55; i++)
            System.out.println();

        System.out.println(outputString);

    }

    public String loadMenu(menu menuType, String additionalString) {

        String input = "";

        switch (menuType) {

            case FIRST: input = getInput("  1 - Start game\n  2 - Show maps\n  3 - Exit game\n\n  ");
                break;
            case START: input = getInput("  1 - Start game\n  2 - Show maps\n  3 - Exit game\n\n  ");
                break;
            case SHOWMAP:   input = getInput("  0 - Exit menu\n" + additionalString);
                break;
            case SELECTMAP: input = getInput(additionalString);
                break;
            case MOVEMENT:  input = getInput(additionalString + "  w = UP\n  s = DOWN\n  a = LEFT\n  d = RIGHT\n\n  ");
                break;

        }

        return input;
    }

    /**
     * Prompts the user for an input.
     * @param promptString - This is a string that will be printed to the screen.
     */
    public String getInput(String promptString) {

        System.out.print(promptString);

        Scanner input = new Scanner(System.in);

        return input.nextLine();

    }

}
