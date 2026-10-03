
package armour;

public class Chainmail extends Armour {
    private int protectionAmount = 62;
    private int dexterityCost = 15;
    private String armourType = "Nano-Suit";
    
    public Chainmail() {
        super();
        setProtectionAmount(protectionAmount);
        setDexterityCost(dexterityCost);
        setArmourType(armourType);
    } // constructor
} // class
