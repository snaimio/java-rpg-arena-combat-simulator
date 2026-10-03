
package util;

import warrior.*;
import armour.*;
import weapon.*;
import arena.Arena;

import java.io.*;
import java.util.Scanner;

public class GameSave {
    private static final String FILE = "savegame.txt";
    
    public GameSave() {}
    
    public boolean saveGame(Warrior p, Armour pa, Weapon pw, Warrior e, Armour ea, Weapon ew, Arena a, int r) {
        try {
            PrintWriter w = new PrintWriter(new FileWriter(FILE));
            w.println("# SPACE WARS SAVE");
            w.println("ROUND," + r);
            if(a != null) w.println("ARENA," + a.getArenaName());
            w.printf("PLAYER,%d,%d,%d,%d,%d,%d\n", getWarType(p), p.getHealth(), p.getStrength(), p.getDexterity(), getArmType(pa), getWepType(pw));
            w.printf("ENEMY,%d,%d,%d,%d,%d,%d\n", getWarType(e), e.getHealth(), e.getStrength(), e.getDexterity(), getArmType(ea), getWepType(ew));
            w.close();
            return true;
        } catch(IOException ex) {
            return false;
        }
    }
    
    public SaveData loadGame() {
        try {
            File f = new File(FILE);
            if(!f.exists()) return null;
            Scanner sc = new Scanner(f);
            SaveData d = new SaveData();
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if(parts[0].equals("ROUND")) {
                    d.round = Integer.parseInt(parts[1]);
                } else if(parts[0].equals("ARENA")) {
                    d.arenaName = parts[1];
                } else if(parts[0].equals("PLAYER")) {
                    d.playerType = Integer.parseInt(parts[1]);
                    d.playerHealth = Integer.parseInt(parts[2]);
                    d.playerStrength = Integer.parseInt(parts[3]);
                    d.playerDexterity = Integer.parseInt(parts[4]);
                    d.playerArmour = Integer.parseInt(parts[5]);
                    d.playerWeapon = Integer.parseInt(parts[6]);
                } else if(parts[0].equals("ENEMY")) {
                    d.enemyType = Integer.parseInt(parts[1]);
                    d.enemyHealth = Integer.parseInt(parts[2]);
                    d.enemyStrength = Integer.parseInt(parts[3]);
                    d.enemyDexterity = Integer.parseInt(parts[4]);
                    d.enemyArmour = Integer.parseInt(parts[5]);
                    d.enemyWeapon = Integer.parseInt(parts[6]);
                }
            }
            sc.close();
            return d;
        } catch(Exception ex) {
            return null;
        }
    }
    
    private int getWarType(Warrior w) {
        String n = w.getClass().getSimpleName();
        if(n.equals("Human")) return 1;
        if(n.equals("Elf")) return 2;
        return 3;
    }
    
    private int getArmType(Armour a) {
        String t = a.getArmourType();
        if(t.equals("Energy Shield")) return 1;
        if(t.equals("Nano-Suit")) return 2;
        return 3;
    }
    
    private int getWepType(Weapon w) {
        String t = w.getWeaponType();
        if(t.equals("Laser Pistol")) return 1;
        if(t.equals("Ion Sword")) return 2;
        return 3;
    }
    
    public static class SaveData {
        public int round = 1;
        public String arenaName = "Neutral Zone";
        public int playerType;
        public int playerHealth;
        public int playerStrength;
        public int playerDexterity;
        public int playerArmour;
        public int playerWeapon;
        public int enemyType;
        public int enemyHealth;
        public int enemyStrength;
        public int enemyDexterity;
        public int enemyArmour;
        public int enemyWeapon;
        
        public SaveData() {}
    }
}