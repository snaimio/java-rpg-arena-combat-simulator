
package weapon;

import armour.Armour;
import warrior.Warrior;

public class Sword extends Weapon {
    private int damageAmount = 47;
    private int dexterityCost = 15;
    private String weaponType = "Ion Sword";
    private int hitChance = 65;
    
    public Sword() {
        super();
        setDamageAmount(damageAmount);
        setDexterityCost(dexterityCost);
        setWeaponType(weaponType);
        setHitChance(hitChance);
    } // constructor
    
    public int strike(Warrior warrior, Weapon weapon, Armour armour) {
        return strike(warrior, weapon, armour, false);
    } // strike()
    
    public int strike(Warrior warrior, Weapon weapon, Armour armour, boolean heavy) {
        return super.strike(warrior, weapon, armour, heavy);
    } // strike()
} // class
