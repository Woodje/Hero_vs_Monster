package com.company;

import javax.swing.text.MutableAttributeSet;

/**
 * GameScene.java - Used for representing the map as a string of characters.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 04/02-2015.
 */
public class CombatScene {

    private Character character1, character2;

    private Character winner, loser;

    private String combatScene;

    public CombatScene(Character character1, Character character2) {

        this.character1 = character1;
        this.character2 = character2;

    }

    public String getCombatScene() {

        int charsAmount = 75;

        charsAmount -= 10;
        charsAmount -= character1.getName().toCharArray().length;
        charsAmount -= character2.getName().toCharArray().length;

        combatScene =   "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                        "  $$ (" + character1.getName() + ")" + addEmptySpace(charsAmount) + "(" + character2.getName() + ") $$\n";

        charsAmount = 75;
        charsAmount -= 20;
        charsAmount -= String.valueOf(character1.getLevel()).toCharArray().length;
        charsAmount -= String.valueOf(character2.getLevel()).toCharArray().length;

        combatScene +=  "  $$ Level: " + character1.getLevel() + addEmptySpace(charsAmount) + "Level: " + character2.getLevel() + " $$\n";

        charsAmount = 75;
        charsAmount -= 24;
        charsAmount -= String.valueOf(character1.getHealth()).toCharArray().length;
        charsAmount -= String.valueOf(character1.getMaxHealth()).toCharArray().length;
        charsAmount -= String.valueOf(character2.getHealth()).toCharArray().length;
        charsAmount -= String.valueOf(character2.getMaxHealth()).toCharArray().length;

        combatScene +=  "  $$ Health: " + character1.getHealth() + "/" + character1.getMaxHealth() +
                        addEmptySpace(charsAmount) + "Health: " + character2.getHealth() + "/" + character2.getMaxHealth() + " $$\n";

        for (int i = 0; i < character1.getSkillArray().length + character2.getSkillArray().length; i++) {

            if (i <= character1.getSkillArray().length - 1) {

                charsAmount = 75;
                charsAmount -= 20;
                charsAmount -= character1.getSkillArray()[i].getName().toCharArray().length;
                charsAmount -= String.valueOf(character1.getSkillArray()[i].getMinDamage() * character1.getDamage()).toCharArray().length;
                charsAmount -= String.valueOf(character1.getSkillArray()[i].getMaxDamage() * character1.getDamage()).toCharArray().length;

                combatScene +=  "  $$ Skill[" + (i + 1) + "]: " + character1.getSkillArray()[i].getName() +
                                " (" + (character1.getSkillArray()[i].getMinDamage() * character1.getDamage()) +
                                "," + (character1.getSkillArray()[i].getMaxDamage() * character1.getDamage()) + ")";

                if (i <= character2.getSkillArray().length - 1) {

                    charsAmount -= 14;

                    charsAmount -= character2.getSkillArray()[i].getName().toCharArray().length;
                    charsAmount -= String.valueOf(character2.getSkillArray()[i].getMinDamage() * character2.getDamage()).toCharArray().length;
                    charsAmount -= String.valueOf(character2.getSkillArray()[i].getMaxDamage() * character2.getDamage()).toCharArray().length;

                    combatScene +=  addEmptySpace(charsAmount) + "Skill[" + (i + 1) + "]: " + character2.getSkillArray()[i].getName() +
                                    " (" + (character2.getSkillArray()[i].getMinDamage() * character2.getDamage())
                                    + "," + (character2.getSkillArray()[i].getMaxDamage() * character2.getDamage()) + ") $$\n";
                }
                else {

                    combatScene += addEmptySpace(charsAmount) + " $$\n";

                }

            }

        }

        return combatScene;

    }

    private String addEmptySpace(int chars) {

        String stringOfChars = "";

        for (int i = 0; i < chars; i++)
            stringOfChars += " ";


        return stringOfChars;
    }

    public String attackWithSkill(String input, boolean attackFromHero) {

        String result = "  No skill entered!";

        int damage = 0;

        if (input.toCharArray().length == 1 && attackFromHero) {

            switch (input.toCharArray()[0]) {

                case '1':   damage = calculateCharacterDamage(character1, 0);
                            character2.setHealth(character2.getHealth() - damage);
                            result = "  You hit " + character2.getName() + " for " + damage + "!";
                            break;

                case '2':   damage = calculateCharacterDamage(character1, 1);
                            character2.setHealth(character2.getHealth() - damage);
                            result = "  You hit " + character2.getName() + " for " + damage + "!";
                            break;

                case '3':   damage = calculateCharacterDamage(character1, 2);
                            character2.setHealth(character2.getHealth() - damage);
                            result = "  You hit " + character2.getName() + " for " + damage + "!";
                            break;

            }

        }
        else if (input.toCharArray().length == 1 && !attackFromHero) {

            damage = calculateCharacterDamage(character2, 0);

            character1.setHealth(character1.getHealth() - damage);

            result = "\n  " + character2.getName() + " hit you for " + damage + "!";

        }

        if (character1.getHealth() == 0) {

            winner = character2;
            loser = character1;

        }
        else if (character2.getHealth() == 0) {

            winner = character1;
            loser = character2;

        }

        return result + "\n";

    }

    private int calculateCharacterDamage(Character character, int skillIndex) {

        int damage = (int) (Math.random() * (character.getSkillArray()[skillIndex].getMaxDamage() - character.getSkillArray()[skillIndex].getMinDamage()) + 1);

        damage += character.getSkillArray()[skillIndex].getMinDamage();

        damage *= character.getDamage();

        return damage;
    }

    public Character getWinner() {

        return winner;

    }

    public Character getLoser() {

        return loser;

    }

    public String getTextures(int textureNumber) {

        String[] textures = new String[4];

        textures[0] =   "  $$                                              _________________        $$\n" +
                        "  $$                                             |  ___       ___  |       $$\n" +
                        "  $$                                             | /   \\     /   \\ |       $$\n" +
                        "  $$                                             | | 0 |     | 0 | |       $$\n" +
                        "  $$                                             | \\___/ , , \\___/ |       $$\n" +
                        "  $$                                             |                 |       $$\n" +
                        "  $$                                             |  /^^^^^^^^^^^\\  |       $$\n" +
                        "  $$       ___                                   \\__\\vvvvvvvvvvv/__/       $$\n" +
                        "  $$      ('_')                              |\\       \\       /       /    $$\n" +
                        "  $$        |                                |~\\       \\     /       /     $$\n" +
                        "  $$        |                                |~~\\_______\\___/_______/      $$\n" +
                        "  $$       /|\\  \\                            |~~/       /   \\       \\      $$\n" +
                        "  $$      / | \\  \\                           |~/       /     \\       \\     $$\n" +
                        "  $$   __/  |  \\__\\ __                       |/       /       \\       \\    $$\n" +
                        "  $$        |      \\  \\                              /         \\           $$\n" +
                        "  $$        |       \\  \\                            /           \\          $$\n" +
                        "  $$       / \\       \\  \\                          /             \\         $$\n" +
                        "  $$      /   \\       \\./                         /               \\        $$\n" +
                        "  $$     /     \\                              ___/                 \\___    $$\n" +
                        "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n";


        textures[1] =   "  $$                                               _________________       $$\n" +
                        "  $$                                              |  ___       ___  |      $$\n" +
                        "  $$                                              | /   \\     /   \\ |      $$\n" +
                        "  $$                                              | | 0 |     | 0 | |      $$\n" +
                        "  $$                                              | \\___/ , , \\___/ |      $$\n" +
                        "  $$                                              |                 |      $$\n" +
                        "  $$         /                                    |  /^^^^^^^^^^^\\  |      $$\n" +
                        "  $$        /   ___        /\"\\                    \\__\\vvvvvvvvvvv/__/      $$\n" +
                        "  $$       /___('_')      /  /                |\\       \\       /       /   $$\n" +
                        "  $$           / \\       /  /                 |~\\       \\     /       /    $$\n" +
                        "  $$          /   \\     /__/                   \\~\\_______\\___/_______/     $$\n" +
                        "  $$         /     \\___/                       /~/       /   \\       \\     $$\n" +
                        "  $$        /         /                       |~/       /     \\       \\    $$\n" +
                        "  $$       | \\       /                        |/       /       \\       \\   $$\n" +
                        "  $$       |  \\                                       /         \\          $$\n" +
                        "  $$       |   \\                                     /           \\         $$\n" +
                        "  $$      /    /                                    /             \\        $$\n" +
                        "  $$     /    /                                    /               \\       $$\n" +
                        "  $$    /    /                                 ___/                 \\___   $$\n" +
                        "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n";

        textures[2] =   "  $$                                               _________________       $$\n" +
                        "  $$                                              |  ___       ___  |      $$\n" +
                        "  $$        __                                    | /   \\     /   \\ |      $$\n" +
                        "  $$        \\  \\                                  | | 0 |     | 0 | |      $$\n" +
                        "  $$         \\  \\                                 | \\___/ , , \\___/ |      $$\n" +
                        "  $$          \\  \\                                |                 |      $$\n" +
                        "  $$  |        \\__\\                               |  /^^^^^^^^^^^\\  |      $$\n" +
                        "  $$  |    ___  \\                                 \\__\\vvvvvvvvvvv/__/      $$\n" +
                        "  $$  |___('_') /\\                            |\\       \\       /       /   $$\n" +
                        "  $$        \\  /  \\                           |~\\       \\     /       /    $$\n" +
                        "  $$         \\/                  <////////#~~~~~~\\_______\\___/_______/     $$\n" +
                        "  $$          \\                  <////////#~~~~~~/       /   \\       \\     $$\n" +
                        "  $$           \\                              |~/       /     \\       \\    $$\n" +
                        "  $$           |\\                             |/       /       \\       \\   $$\n" +
                        "  $$           | \\                                    /         \\          $$\n" +
                        "  $$           |  \\                                  /           \\         $$\n" +
                        "  $$          /    |                                /             \\        $$\n" +
                        "  $$         /     |                               /               \\       $$\n" +
                        "  $$        /      |                           ___/                 \\___   $$\n" +
                        "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n";

        textures[3] =   "  $$                                               _________________       $$\n" +
                        "  $$                                              |  ___       ___  |      $$\n" +
                        "  $$                                              | /   \\     /   \\ |      $$\n" +
                        "  $$                                              | | 0 |     | 0 | |      $$\n" +
                        "  $$                                              | \\___/ , , \\___/ |      $$\n" +
                        "  $$                                              |                 |      $$\n" +
                        "  $$         /                                    |  /^^^^^^^^^^^\\  |      $$\n" +
                        "  $$        /   ___        /\"\\                    \\__\\vvvvvvvvvvv/__/      $$\n" +
                        "  $$       /___('_')      /  /                |\\       \\       /       /   $$\n" +
                        "  $$           / \\       /  /                 |~\\       \\     /       /    $$\n" +
                        "  $$          /   \\     /__/     <////////#~~~~~~\\_______\\___/_______/     $$\n" +
                        "  $$         /     \\___/         <////////#~~~~~~/       /   \\       \\     $$\n" +
                        "  $$        /         /                       |~/       /     \\       \\    $$\n" +
                        "  $$       | \\       /                        |/       /       \\       \\   $$\n" +
                        "  $$       |  \\                                       /         \\          $$\n" +
                        "  $$       |   \\                                     /           \\         $$\n" +
                        "  $$      /    /                                    /             \\        $$\n" +
                        "  $$     /    /                                    /               \\       $$\n" +
                        "  $$    /    /                                 ___/                 \\___   $$\n" +
                        "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n";

        return textures[textureNumber];

    }

}
