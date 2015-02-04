package com.company;

/**
 * GameScene.java - Used for representing the map as a string of characters.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 04/02-2015.
 */
public class CombatScene {

    private Character character1, character2;

    private String combatScene;

    public CombatScene(Character character1, Character character2) {

        this.character1 = character1;
        this.character2 = character2;

    }

    public String getCombatScene() {

        int chars = 71;

        chars -= character1.getName().toCharArray().length;
        chars -= character2.getName().toCharArray().length;

        combatScene =   "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                        "  (" + character1.getName() + ")" + addChars(chars) + "(" + character2.getName() + ")";


        combatScene =   "Level: " + character1.getLevel() + "\n" +
                        "  Health: " + character1.getHealth() + "/" + character1.getMaxHealth();

        return combatScene;

    }

    private String addChars(int chars) {

        String stringOfChars = "";

        for (int i = 0; i < chars; i++)
            stringOfChars += " ";


        return stringOfChars;
    }

    public Character getWinner() {

        return character1;

    }

    public Character getLoser() {

        return character2;

    }

}
