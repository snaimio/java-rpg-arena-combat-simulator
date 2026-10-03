
package util;

import java.security.SecureRandom;

public class PlayByPlay {
    private SecureRandom random = new SecureRandom();
    
    public PlayByPlay() {}
    
    public String getHitDescription(String weapon, int dmg, boolean isPlayer) {
        String[] lines;
        
        switch(weapon) {
            case "Laser Pistol":
                lines = new String[]{"fires a laser blast", "zaps the target", "shoots with precision"};
                break;
            case "Ion Sword":
                lines = new String[]{"slashes with the blade", "strikes with energy", "cuts through armor"};
                break;
            case "Plasma Axe":
                lines = new String[]{"smashes with the axe", "cleaves with fury", "crushes the enemy"};
                break;
            default:
                lines = new String[]{"lands a hit", "strikes true"};
                break;
        }
        
        int idx = random.nextInt(lines.length);
        String action = lines[idx];
        
        String severity;
        if(dmg > 100) {
            severity = "DEVASTATING";
        } else if(dmg > 60) {
            severity = "HEAVY";
        } else if(dmg > 30) {
            severity = "SOLID";
        } else {
            severity = "light";
        }
        
        return action + " for " + severity + " " + dmg + " damage!";
    }
    
    public String getMissDescription(String weapon, boolean isPlayer) {
        String[] lines = {"swings wildly and misses", "attack is dodged", "fumbles the attack", "strikes nothing but air"};
        int idx = random.nextInt(lines.length);
        return lines[idx] + "!";
    }
    
    public String getNoDexterityDescription(String weapon, boolean isPlayer) {
        String[] lines = {"tries to power up but runs out of stamina", "cannot gather enough energy for the attack",
            "pushes too hard and loses momentum", "fails to commit to the strike"};
        int idx = random.nextInt(lines.length);
        return lines[idx] + "!";
    }
    
    public String getHeavyAttackWindup(String weapon, boolean isPlayer) {
        String[] lines = {"winds up for a heavy strike", "prepares a powerful attack", "gathers strength for a big hit"};
        int idx = random.nextInt(lines.length);
        return lines[idx];
    }
}
