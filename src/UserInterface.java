package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * UserInterface.java - Used for displaying the game and retrieving inputs.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 02/02-2015.
 */
public class UserInterface {

    /**
     * Prints the provided string to the screen.
     * @param outputString - This is a string that will be printed to the screen.
     */
    public void drawToScreen(String outputString) {

        for (int i = 0; i < 30; i++)
            System.out.println();

        System.out.println(outputString);

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
