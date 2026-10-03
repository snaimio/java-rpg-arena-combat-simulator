
package arena;

import java.security.SecureRandom;

public class Arena {
    private SecureRandom randNum = new SecureRandom();
    private String arenaName;
    private String arenaDescription;
    private int playerModifier;
    private int enemyModifier;
    private String color;
    
    private String reset = "\u001B[0m";
    private String red = "\u001B[31m";
    private String green = "\u001B[32m";
    private String cyan = "\u001B[36m";
    private String purple = "\u001B[35m";
    private String white = "\u001B[37m";
    
    public Arena() {
        pick();
    }
    
    public Arena(String arenaName) {
        if(!loadByName(arenaName)) {
            pick();
        }
    }
    
    private void pick() {
        int r = randNum.nextInt(5) + 1;
        switch(r) {
            case 1:
                arenaName = "Asteroid Field";
                arenaDescription = "Dense asteroids block attacks";
                playerModifier = -4;
                enemyModifier = -4;
                color = cyan;
                break;
            case 2:
                arenaName = "Solar Flare";
                arenaDescription = "Intense heat drains energy";
                playerModifier = -6;
                enemyModifier = 0;
                color = red;
                break;
            case 3:
                arenaName = "Crystal Caverns";
                arenaDescription = "Reflections aid your aim";
                playerModifier = 4;
                enemyModifier = -5;
                color = green;
                break;
            case 4:
                arenaName = "Dark Nebula";
                arenaDescription = "Darkness reduces visibility";
                playerModifier = -5;
                enemyModifier = -5;
                color = purple;
                break;
            default:
                arenaName = "Neutral Zone";
                arenaDescription = "Normal conditions";
                playerModifier = 0;
                enemyModifier = 0;
                color = white;
                break;
        } // switch
    } // pick()
    
    private boolean loadByName(String arenaName) {
        if(arenaName == null) {
            return false;
        }
        
        switch(arenaName) {
            case "Asteroid Field":
                this.arenaName = "Asteroid Field";
                arenaDescription = "Dense asteroids block attacks";
                playerModifier = -4;
                enemyModifier = -4;
                color = cyan;
                return true;
            case "Solar Flare":
                this.arenaName = "Solar Flare";
                arenaDescription = "Intense heat drains energy";
                playerModifier = -6;
                enemyModifier = 0;
                color = red;
                return true;
            case "Crystal Caverns":
                this.arenaName = "Crystal Caverns";
                arenaDescription = "Reflections aid your aim";
                playerModifier = 4;
                enemyModifier = -5;
                color = green;
                return true;
            case "Dark Nebula":
                this.arenaName = "Dark Nebula";
                arenaDescription = "Darkness reduces visibility";
                playerModifier = -5;
                enemyModifier = -5;
                color = purple;
                return true;
            case "Neutral Zone":
                this.arenaName = "Neutral Zone";
                arenaDescription = "Normal conditions";
                playerModifier = 0;
                enemyModifier = 0;
                color = white;
                return true;
            default:
                return false;
        } // switch
    } // loadByName()
    
    public String getArenaName() {
        return arenaName;
    } // getArenaName()
    
    public int applyModifier(int dmg, boolean isPlayer) {
        int mod = isPlayer ? playerModifier : enemyModifier;
        if(mod > 0) {
            dmg = dmg + (dmg * mod / 100);
        } else if(mod < 0) {
            dmg = dmg - (dmg * Math.abs(mod) / 100);
        }
        if(dmg < 1) dmg = 1;
        return dmg;
    } // applyModifier()
    
    public void printArenaInfo() {
        System.out.println();
        System.out.println(color + "========== A R E N A ==========");
        System.out.println("Location: " + reset + white + arenaName);
        System.out.println(color + "Effect:   " + reset + white + arenaDescription);
        if(playerModifier != 0) {
            String s = playerModifier > 0 ? "+" : "";
            System.out.println(color + "Player:   " + reset + (playerModifier > 0 ? green : red) + s + playerModifier + "% damage");
        }
        if(enemyModifier != 0) {
            String s = enemyModifier > 0 ? "+" : "";
            System.out.println(color + "Enemy:    " + reset + (enemyModifier > 0 ? green : red) + s + enemyModifier + "% damage");
        }
        System.out.println(color + "===============================" + reset);
    } // printArenaInfo()
} // class
