
package warrior;

public class Human extends Warrior {
    private int minHealth = 340;
    private int bonusHealth = 120;
    private int minStrength = 66;
    private int bonusStrength = 34;
    private int minDexterity = 68;
    private int bonusDexterity = 28;
    
    public Human() {
        super();
        setHealth(randNum.nextInt(bonusHealth) + minHealth);
        setStrength(randNum.nextInt(bonusStrength) + minStrength);
        setDexterity(randNum.nextInt(bonusDexterity) + minDexterity);
    } // constructor
} // class
