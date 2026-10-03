
package warrior;

public class Orc extends Warrior {
    private int minHealth = 360;
    private int bonusHealth = 120;
    private int minStrength = 70;
    private int bonusStrength = 36;
    private int minDexterity = 58;
    private int bonusDexterity = 20;
    
    public Orc() {
        super();
        setHealth(randNum.nextInt(bonusHealth) + minHealth);
        setStrength(randNum.nextInt(bonusStrength) + minStrength);
        setDexterity(randNum.nextInt(bonusDexterity) + minDexterity);
    } // constructor
} // class
