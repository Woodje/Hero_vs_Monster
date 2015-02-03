package com.company;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import com.company.UserInterface.menu;

/**
 * Created by Woodje on 02-02-2015.
 */
public class GameEngine {

    private ArrayList<Character> characters;

    private Map map;

    private UserInterface userInterface;

    private String promptString;

    public GameEngine() {

        userInterface = new UserInterface();
        map = new Map();
        characters = new ArrayList<Character>();

    }

    public void initializeGame() {

        userInterface.drawToScreen("  Welcome\n");

        int input = convertToInteger(userInterface.loadMenu(menu.FIRST, ""));

        switch (input) {

            case 1:  startGame();
                     break;
            case 2:  listMaps(true);
                     break;
            case 3:  System.exit(0);
            default: initializeGame();

        }

    }

    private void startGame() {

        createCharacter(true);
        createCharacter(false);

        userInterface.getInput("Press 'ENTER' to start playing");

        gameLoop();

    }

    private void gameLoop() {

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



        gameLoop();

    }

    private void moveCharacter(Character character, Point point) {

        for (Character characterToMove : characters) {

            if (characterToMove == character) {

                Point oldLocation = characterToMove.getLocation();

                Point newLocation = new Point(point.x + characterToMove.getLocation().x, point.y + characterToMove.getLocation().y);

                if (map.moveTextureLocation(characterToMove.getTexture(), oldLocation, newLocation).contains("Success"))
                    characterToMove.setLocation(newLocation);

            }

        }

    }

    private void createCharacter(boolean userDefined) {

        if (userDefined) {

            userInterface.drawToScreen("Create hero");

            Character hero = new Character(userInterface.getInput("Name your hero: "), 3);

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

            Character monster = new Character("MONSTER", 1);

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

                    monster = new Character("MONSTER" + String.valueOf(i), 1);

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

    private void listMaps(boolean showOnly) {

        int input = -1;

        if (showOnly) {

            userInterface.drawToScreen("  Display Maps");
            input = convertToInteger(userInterface.loadMenu(menu.SHOWMAP, map.getMaps()));

        }
        else {

            userInterface.drawToScreen("  Select Map");
            input = convertToInteger(userInterface.loadMenu(menu.SELECTMAP, map.getMaps()));

        }

        switch (input) {

            case 0:  if (showOnly) {

                        initializeGame();
                        break;
                     }
            default: if (input <= map.getMapsFiles().length) {

                        map.setMap(map.getMapFile(input));

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
