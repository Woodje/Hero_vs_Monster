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
     * Prints the specified array list to the screen.
     * @param outputStrings - This is an array list of strings that will be printed to the screen.
     */
    public void drawToScreen(ArrayList<String> outputStrings) {

        for (String output : outputStrings)
            System.out.println(output);

    }

    /** Return input from the user */
    public String getInput() {

        Scanner input = new Scanner(System.in);

        return input.nextLine();

    }

}
