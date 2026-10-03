
package warrior;

import java.security.SecureRandom;

public abstract class Warrior {
    protected SecureRandom randNum = new SecureRandom();
    private int health;
    private int strength;
    private int dexterity;
    
    public Warrior() {} // constructor
    
    // getters
    public int getHealth() {
        return this.health;
    }
    public int getStrength() {
        return this.strength;
    }
    public int getDexterity() {
        return this.dexterity;
    }
    
    // setters
    public void setHealth(int health) {
        this.health = health;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    
    public void takeDamage(int damageAmount) {
        if(damageAmount > 0)
            this.health -= damageAmount;
    } // takeDamage()
} // class
