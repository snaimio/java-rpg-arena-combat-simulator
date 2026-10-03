
package warrior;

public class Elf extends Warrior {
    private int minHealth = 320;
    private int bonusHealth = 110;
    private int minStrength = 64;
    private int bonusStrength = 30;
    private int minDexterity = 82;
    private int bonusDexterity = 30;
    
    public Elf() {
        super();
        setHealth(randNum.nextInt(bonusHealth) + minHealth);
        setStrength(randNum.nextInt(bonusStrength) + minStrength);
        setDexterity(randNum.nextInt(bonusDexterity) + minDexterity);
    } // constructor
} // class
