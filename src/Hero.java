/**
 * Hero.java - Represents a Hero.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 04/02-2015
 * @see Character
 */
public class Hero extends Character {

    /** Represents the experience. */
    private int experience;

    /**
     * Constructor.
     * @param name - The name of the hero.
     * @param skills - Decides how many skills the monster has.
     */
    public Hero (String name, int skills) {

        super(name, skills);

    }

    /**
     * Sets the experience.
     * @param experience - The amount of experience.
     */
    public void setExperience(int experience) {

        this.experience = experience;

    }

    /** Gets the experience */
    public int getExperience() {

      return experience;

    }

}
