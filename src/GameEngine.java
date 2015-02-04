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

        userInterface.drawToScreen("  Welcome\n  --------------");

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

            userInterface.drawToScreen(getStats() + map.getMap());

            processUserInput(userInterface.loadMenu(menu.MOVEMENT, "  Where to go?  "));

            userInterface.drawToScreen(map.getMap());

            moveMonsters();

            Character[] charactersFighting = getCharactersFighting();

            if (charactersFighting != null) {

                CombatScene combatScene = new CombatScene(charactersFighting[0], charactersFighting[1]);

                userInterface.drawToScreen(getStats() + map.getMap());

                userInterface.getInput("  You have entered combat, press 'ENTER' to start the the fight!");

                userInterface.drawToScreen(combatScene.getCombatScene());

                userInterface.getInput("  Winner: " + combatScene.getWinner().getName() + "\n  Loser: " + combatScene.getLoser().getName());


            }

        }

    }

    private Character[] getCharactersFighting() {

        Character[] charactersFighting = new Character[2];

        int x = 0;

        for (int i = 0; i < characters.size(); i++)
            if (characters.get(i).getTexture() == map.fightTexture) {

                charactersFighting[x] = characters.get(i);

                x++;

            }

        if (x < 1)
            return null;

        return charactersFighting;

    }

    /** Return a string representing the stats for each character in the game. */
    public String getStats() {

        String statsIndicator = "";

        for (Character character : characters)
            if (character instanceof Hero) {

                statsIndicator +=   "  (" + character.getName() + ") - " +
                        "Level: " + character.getLevel() + " - " +
                        "Health: " + character.getHealth() + "/" + character.getMaxHealth() + " - " +
                        "XP: " + ((Hero) character).getExperience() + "/" + ((Hero) character).getMaxExperience() + "\n";
            }
            else {

                statsIndicator +=   "  (" + character.getName() + ") - " +
                        "Level: " + character.getLevel() + " - " +
                        "Health: " + character.getHealth() + "/" + character.getMaxHealth() + "\n";
            }

        return statsIndicator;

    }

    /**
     * Do the appropriate action according to the users input.
     * @param input - This should be the the users input.
     */
    private void processUserInput(String input) {

        if (input.toCharArray().length == 1) {

            switch (input.toCharArray()[0]) {

                case 'w':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(0, -1));

                            break;

                case 's':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(0, 1));

                            break;

                case 'a':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(-1, 0));

                            break;

                case 'd':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(1, 0));

                            break;

            }

        }

    }

    /**
     * Move all monsters within the characters list.
     * X and Y movements are randomly chosen from -1 to 1.
     */
    private void moveMonsters() {

        for (Character monster : characters)
            if (monster instanceof Monster) {

                int xMovement = (int) (Math.random() * 3) - 1,
                    yMovement = (int) (Math.random() * 3) - 1;

                moveCharacter(monster, new Point(xMovement, yMovement));

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

        String result = map.moveTextureLocation(oldLocation, newLocation);

        if (result.contains("Success")) {

            character.setLocation(newLocation);

            if (result.contains("Hero") || result.contains("Monster")) {

                for (Character characterCollided : characters)
                    if (character.getLocation().equals(characterCollided.getLocation()))
                        characterCollided.setTexture(map.fightTexture);

            }

        }

    }

    /**
     * Create either a user defined character (hero) or create one ore more monsters depending on the map.
     * @param userDefined - True if the user should define a hero. False if monsters should be created.
     */
    private void createCharacter(boolean userDefined) {

        if (userDefined) {

            userInterface.drawToScreen("  Create hero\n  ---------------");

            Hero hero = new Hero(userInterface.getInput("  Name your hero: "), 3);

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

            Monster monster = new Monster("MONSTER1", 1);

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

                    monster = new Monster("MONSTER" + String.valueOf(i + 1), 1);

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
