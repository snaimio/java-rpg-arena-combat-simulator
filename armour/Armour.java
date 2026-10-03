
package armour;

public abstract class Armour {
    private int protectionAmount;
    private int dexterityCost;
    private String armourType;
    
    public Armour() {}
    
    // getters
    public int getProtectionAmount() {
        return this.protectionAmount;
    }
    public int getDexterityCost() {
        return this.dexterityCost;
    }
    public String getArmourType() {
        return this.armourType;
    }
    
    // setters
    public void setProtectionAmount(int protectionAmount) {
        this.protectionAmount = protectionAmount;
    }
    public void setDexterityCost(int dexterityCost) {
        this.dexterityCost = dexterityCost;
    }
    public void setArmourType(String armourType) {
        this.armourType = armourType;
    }
} // class
