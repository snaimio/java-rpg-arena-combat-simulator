
package weapon;
import warrior.*;
import armour.*;

import java.security.SecureRandom;

public abstract class Weapon {
    private static final int HEAVY_ATTACK_EXTRA_COST = 10;
    private int damageAmount;
    private int dexterityCost;
    private String weaponType;
    private int hitChance;
    protected SecureRandom randNum = new SecureRandom();
    
    public Weapon() {}
    
    public static class AttackResult {
        private boolean hitTarget;
        private boolean enoughDexterity;
        private boolean heavyAttack;
        private int damageDone;
        private int dexterityCost;
        
        public AttackResult(boolean hitTarget, boolean enoughDexterity, boolean heavyAttack,
            int damageDone, int dexterityCost) {
            this.hitTarget = hitTarget;
            this.enoughDexterity = enoughDexterity;
            this.heavyAttack = heavyAttack;
            this.damageDone = damageDone;
            this.dexterityCost = dexterityCost;
        } // constructor
        
        public boolean didHitTarget() {
            return this.hitTarget;
        }
        public boolean hadEnoughDexterity() {
            return this.enoughDexterity;
        }
        public boolean isHeavyAttack() {
            return this.heavyAttack;
        }
        public int getDamageDone() {
            return this.damageDone;
        }
        public int getDexterityCost() {
            return this.dexterityCost;
        }
    } // class
    
    // getters
    public int getDamageAmount() {
        return this.damageAmount;
    }
    public int getDexterityCost() {
        return this.dexterityCost;
    }
    public String getWeaponType() {
        return this.weaponType;
    }
    public int getHitChance() {
        return this.hitChance;
    }
    
    // setters
    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }
    public void setDexterityCost(int dexterityCost) {
        this.dexterityCost = dexterityCost;
    }
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }
    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }
    
    protected int calcBonus(Warrior warrior, Weapon weapon, Armour armour) {
        return (warrior.getDexterity() / 20) - ((weapon.getDexterityCost() + armour.getDexterityCost()) / 20);
    }
    
    protected int calcDamage(Warrior warrior, Weapon weapon) {
        return weapon.getDamageAmount() + randNum.nextInt(Math.max(1, warrior.getStrength() / 10)) + 6;
    }
    
    protected boolean isHit(Warrior warrior, Weapon weapon, Armour armour) {
        int roll = randNum.nextInt(100) + 1;
        int bonus = calcBonus(warrior, weapon, armour);
        int chance = hitChance + bonus;
        if(chance > 88) chance = 88;
        if(chance < 40) chance = 40;
        return roll <= chance;
    } // isHit()
    
    public int strike(Warrior warrior, Weapon weapon, Armour armour) {
        return attack(warrior, weapon, armour, false).getDamageDone();
    } // strike()
    
    public int strike(Warrior warrior, Weapon weapon, Armour armour, boolean heavy) {
        return attack(warrior, weapon, armour, heavy).getDamageDone();
    } // strike()
    
    public AttackResult attack(Warrior warrior, Weapon weapon, Armour armour, boolean heavy) {
        int damage = 0;
        int currentDamage = damageAmount;
        int currentHitChance = hitChance;
        int attackCost = 0;
        
        if(heavy) {
            currentDamage = (int)(damageAmount * 1.3);
            currentHitChance = hitChance - 8;
            attackCost = weapon.getDexterityCost() + HEAVY_ATTACK_EXTRA_COST;
        }
        
        if(heavy && warrior.getDexterity() < attackCost) {
            return new AttackResult(false, false, true, 0, attackCost);
        }
        
        if(heavy) {
            warrior.setDexterity(warrior.getDexterity() - attackCost);
        }
        
        int oldDamage = damageAmount;
        int oldHitChance = hitChance;
        damageAmount = currentDamage;
        hitChance = currentHitChance;
        
        if(isHit(warrior, weapon, armour)) {
            damage = calcDamage(warrior, weapon);
            if(damage < 8)
                damage = 8;
        }
        
        damageAmount = oldDamage;
        hitChance = oldHitChance;
        return new AttackResult(damage > 0, true, heavy, damage, attackCost);
    } // attack()
} // class
