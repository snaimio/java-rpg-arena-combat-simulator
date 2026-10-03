
package weapon;

import armour.Armour;
import warrior.Warrior;

public class Axe extends Weapon {
    private int damageAmount = 53;
    private int dexterityCost = 30;
    private String weaponType = "Plasma Axe";
    private int hitChance = 59;
    
    public Axe() {
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
