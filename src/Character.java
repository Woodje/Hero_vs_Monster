import java.awt.Point;

/**
 * Character.java - Represents a single character.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 02/02-2015
 */
public class Character {

    /* The name of the character. */
    private String name;

    /* The characters level. */
    private  int level;

    /* The characters amount of health. */
    private int health;

    /* The amount of damage that the character can deal. */
    private int damage;

    /* The characters position on the map. */
    private Point positionPoint;

    /* The characters skills. */
    private Skill[] skillArray;

    /**
     * Constructor.
     * @param name - The name of the character.
     * @param skills - Decide how many skills the character has.
     */
    public Character(String name, int skills) {

        this.name = name;
        skillArray = new Skill[skills];

    }

    /* Gets the name of the character. */
    public String getName() {

        return name;

    }

    /**
     * Sets the level of the character.
     * @param level - The level.
     */
    public void setLevel(int level) {

        this.level = level;

    }

    /* Gets the level of the character. */
    public int getLevel() {

        return  level;

    }

    /**
     * Sets the amount of health on the character.
     * @param health - Amount of health.
     */
    public void setHealth(int health) {

        this.health = health;

    }

    /* Gets the health of the character. */
    public int getHealth() {

        return health;

    }

    /**
     * Sets the amount of damage that the character can deal.
     * @param damage - the damage amount.
     */
    public void setDamage(int damage) {

        this.damage = damage;

    }

    /* Gets the amount of damage that the character can deal. */
    public int getDamage() {

        return damage;

    }

    /**
     * Sets the characters position on the map.
     * @param positionPoint - Position on the map.
     */
    public void setPositionPoint(Point positionPoint) {

        this.positionPoint = positionPoint;

    }

    /* Gets the position of the character on the map.*/
    public Point getPositionPoint() {

        return positionPoint;

    }

    /**
     * If the index is higher then the length of the array, throw and exception.
     * Else, add the skill to the array.
     * @param skill - The skill.
     * @param index - The index in the array.
     */
    public void setSkillArray(Skill skill, int index) {

        if(index > skillArray.length - 1) {

            throw new ArrayIndexOutOfBoundsException("Only " + skillArray.length + " skills allowed. Index: " + index + " does not exist.");
        }

        skillArray[index] = skill;

    }

    /* Gets all the skills in the array. */
    public Skill[] getSkillArray() {

        return skillArray;

    }

}