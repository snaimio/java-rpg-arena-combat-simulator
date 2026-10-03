
package armour;

public class Platemail extends Armour {
    private int protectionAmount = 76;
    private int dexterityCost = 30;
    private String armourType = "Plasma Armour";
    
    public Platemail() {
        super();
        setProtectionAmount(protectionAmount);
        setDexterityCost(dexterityCost);
        setArmourType(armourType);
    } // constructor
} // class
