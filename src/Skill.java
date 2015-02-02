/**
 * Skill.java - Represents a simple skill.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 02/02-2015
 */
public class Skill {

    private String name;
    private int minDamage;
    private int maxDamage;


    public Skill(String name, int minDamage, int maxDamage) {

    }

    /* Gets the name of the skill */
    public String getName() {

        return name;

    }

    /* Gets the minimum damage of the skill */
    public int getMinDamage() {

        return minDamage;

    }

    /* Gets the maximum damage of the skill */
    public int getMaxDamage() {

        return maxDamage;

    }

}
