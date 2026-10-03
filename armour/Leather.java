
package armour;

public class Leather extends Armour {
    private int protectionAmount = 48;
    private int dexterityCost = 5;
    private String armourType = "Energy Shield";
    
    public Leather() {
        super();
        setProtectionAmount(protectionAmount);
        setDexterityCost(dexterityCost);
        setArmourType(armourType);
    } // constructor
} // class
