
package weapon;

import armour.Armour;
import warrior.Warrior;

public class Dagger extends Weapon {
    private int damageAmount = 41;
    private int dexterityCost = 5;
    private String weaponType = "Laser Pistol";
    private int hitChance = 71;
    
    public Dagger() {
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
