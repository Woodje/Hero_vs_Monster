package com.company;

import java.awt.*;
import java.util.ArrayList;

import com.company.UserInterface.menu;

/**
 * GameEngine - Used for controlling the basic logic of the game.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 03/02-2015.
 */
public class GameEngine {

    /** This is the list used for storing all characters in the game. */
    private ArrayList<Character> characters;

    /** This is used for map operations. */
    private Map map;

    /** This is used for user interfacing. */
    private UserInterface userInterface;

    /**
     * Constructor.
     * Instantiating of: userInterface, map and characters.
     */
    public GameEngine() {

        userInterface = new UserInterface();
        map = new Map();
        characters = new ArrayList<Character>();

    }

    /** Represent the user for the first menu. */
    public void initializeGame() {

        userInterface.drawToScreen("  Welcome\n");

        int input = convertToInteger(userInterface.loadMenu(menu.FIRST, ""));

        switch (input) {

            case 1:  startGame();
                     break;

            case 2:  listMaps(true);
                     break;

            case 3:  exitGame();

            default: initializeGame();

        }

    }

    /** Starts up the necessary precautions before starting the games loop. */
    private void startGame() {

        createCharacter(true);

        createCharacter(false);

        userInterface.getInput("Press 'ENTER' to start playing");

        gameLoop();

    }

    /**
     *  This is the games loop from where the actual basic logic of the game takes place.
     *  The user is prompt for an input for each run through.
     */
    private void gameLoop() {

        while(true) {

            userInterface.drawToScreen(map.getMap());

            String input = userInterface.loadMenu(menu.MOVEMENT, "  Where to go?\n");

            if (input.equals("w"))
                moveCharacter(characters.get(0), new Point(0, -1));
            if (input.equals("s"))
                moveCharacter(characters.get(0), new Point(0, 1));
            if (input.equals("a"))
                moveCharacter(characters.get(0), new Point(-1, 0));
            if (input.equals("d"))
                moveCharacter(characters.get(0), new Point(1, 0));

        }
    }

    /** Exits the game and prints a little message */
    private void exitGame() {

        userInterface.drawToScreen("Thank you for playing...");

        System.exit(0);

    }

    /**
     * Move the given character with the amount of values from the provided point.
     * @param character - This is the character that should be moved.
     * @param point - This is for how much the characters location should be moved.
     */
    private void moveCharacter(Character character, Point point) {

        Point oldLocation = character.getLocation();

        Point newLocation = new Point(point.x + character.getLocation().x, point.y + character.getLocation().y);

        if (map.moveTextureLocation(oldLocation, newLocation).contains("Success"))
            character.setLocation(newLocation);

    }

    /**
     * Create either a user defined character (hero) or create one ore more monsters depending on the map.
     * @param userDefined - True if the user should define a hero. False if monsters should be created.
     */
    private void createCharacter(boolean userDefined) {

        if (userDefined) {

            userInterface.drawToScreen("  Create hero\n  ---------------");

            Character hero = new Character(userInterface.getInput("  Name your hero: "), 3);

            hero.setLevel(1);

            hero.setHealth(100);

            hero.setDamage(1);

            hero.setSkillArray(new Skill("Basic", 1, 10), 0);
            hero.setSkillArray(new Skill("Medium", 3, 6), 1);
            hero.setSkillArray(new Skill("High", 6, 8), 2);

            hero.setTexture(map.heroTexture);

            listMaps(false);

            if (map.getTextureLocations(hero.getTexture()).size() == 0) {

                if (map.getTextureLocations(map.floorTexture).size() == 0) {

                    userInterface.getInput("Error using map, no floor textures detected.\nPress 'ENTER' to start over...");
                    characters.clear();
                    createCharacter(true);

                }
                else {

                    hero.setLocation(map.getTextureLocations(map.floorTexture).get(0));

                    characters.add(hero);

                    map.setTextureLocation(hero.getTexture(), hero.getLocation());

                }

            }
            else {

                hero.setLocation(map.getTextureLocations(hero.getTexture()).get(0));

                characters.add(hero);

            }

        }
        else {

            Character monster = new Character("MONSTER1", 1);

            monster.setLevel(1);

            monster.setHealth(100);

            monster.setDamage(1);

            monster.setSkillArray(new Skill("Basic", 1, 10), 0);

            monster.setTexture(map.monsterTexture);

            if (map.getTextureLocations(monster.getTexture()).size() == 0) {

                if (map.getTextureLocations(map.floorTexture).size() == 0){

                    userInterface.getInput("Error using map, no floor textures detected.\nPress 'ENTER' to start over...");
                    characters.clear();
                    createCharacter(false);

                }
                else {

                    monster.setLocation(map.getTextureLocations(map.floorTexture).get(map.getTextureLocations(map.floorTexture).size() - 1));

                    characters.add(monster);

                    map.setTextureLocation(monster.getTexture(), monster.getLocation());

                }

            }
            else {

                for (int i = 0; i < map.getTextureLocations(map.monsterTexture).size(); i++) {

                    monster = new Character("MONSTER" + String.valueOf(i + 1), 1);

                    monster.setLevel(1);

                    monster.setHealth(100);

                    monster.setDamage(1);

                    monster.setSkillArray(new Skill("Basic", 1, 10), 0);

                    monster.setTexture(map.monsterTexture);

                    monster.setLocation(map.getTextureLocations(monster.getTexture()).get(i));

                    characters.add(monster);

                }

            }

        }

    }

    /**
     * Represent the user for either a menu displaying the maps or a menu for selecting a map.
     * @param showOnly - True if a displaying menu should be shown. False if a selection menu should be shown.
     */
    private void listMaps(boolean showOnly) {

        int input;

        if (showOnly) {

            userInterface.drawToScreen("  Display Maps\n  -------------");
            input = convertToInteger(userInterface.loadMenu(menu.SHOWMAP, map.getMaps()));

        }
        else {

            userInterface.drawToScreen("  Select Map\n  ------------");
            input = convertToInteger(userInterface.loadMenu(menu.SELECTMAP, map.getMaps()));

        }

        switch (input) {

            case 0:  if (showOnly) {

                        initializeGame();
                        break;
                     }
            default: if (input <= map.getMapsFiles().length && input >= 0) {

                        map.setMap(map.getMapFileName(input));

                        if (showOnly) {

                            userInterface.drawToScreen("");
                            userInterface.getInput(map.getMap() + "Press 'ENTER' to continue...");
                            listMaps(true);

                        }
                     }
                     else
                        if (showOnly)
                            listMaps(true);
                        else
                            listMaps(false);

        }

    }

    /**
     * Convert a string to an integer if possible, if not possible then a value of -1 is returned instead.
     * @param string - This is the string to be converted.
     */
    private int convertToInteger(String string) {

        int value;

        try {

            value = Integer.parseInt(string);

        } catch(NumberFormatException e) {

            return -1;
        }

        return value;
    }

}
