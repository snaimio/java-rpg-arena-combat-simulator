package util;

import armour.Armour;
import arena.Arena;
import warrior.Warrior;
import weapon.Weapon;

public class Ink {
    private final String reset = "\u001B[0m";
    private final String bold = "\u001B[1m";

    private final String gold = "\u001B[38;5;220m";
    private final String amber = "\u001B[38;5;214m";
    private final String cyan = "\u001B[38;5;51m";
    private final String azure = "\u001B[38;5;45m";
    private final String blue = "\u001B[38;5;39m";
    private final String violet = "\u001B[38;5;141m";
    private final String magenta = "\u001B[38;5;213m";
    private final String rose = "\u001B[38;5;204m";
    private final String mint = "\u001B[38;5;85m";
    private final String teal = "\u001B[38;5;80m";
    private final String green = "\u001B[92m";
    private final String yellow = "\u001B[93m";
    private final String red = "\u001B[91m";
    private final String white = "\u001B[97m";
    private final String silver = "\u001B[38;5;252m";
    private final String gray = "\u001B[38;5;245m";
    private final String charcoal = "\u001B[38;5;238m";

    private final Validator validator = new Validator();
    private final int boxWidth = 74;

    public Ink() {}

    private String plain(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private int visibleLength(String text) {
        return plain(text).length();
    }

    private String center(String text) {
        int padding = Math.max(0, (boxWidth - visibleLength(text)) / 2);
        return " ".repeat(padding) + text;
    }

    private String gradientRule() {
        String[] palette = {cyan, azure, blue, violet, magenta, rose, gold, amber};
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < boxWidth; i++) {
            line.append(palette[i % palette.length]).append("═");
        }
        line.append(reset);
        return line.toString();
    }

    private void printCentered(String text) {
        System.out.println(center(text));
    }

    private void printBorderTop() {
        printCentered(cyan + "╔" + gradientRule() + cyan + "╗" + reset);
    }

    private void printBorderBottom() {
        printCentered(cyan + "╚" + gradientRule() + cyan + "╝" + reset);
    }

    private void printDivider(String accent) {
        printCentered(accent + "╟" + gray + "─".repeat(boxWidth) + accent + "╢" + reset);
    }

    private void printRow(String content, String accent) {
        int pad = Math.max(0, boxWidth - visibleLength(content));
        printCentered(accent + "║ " + content + " ".repeat(pad) + accent + "║" + reset);
    }

    private void printTitle(String title, String accent) {
        String text = bold + accent + "◆ " + title + " ◆" + reset;
        int leftPad = Math.max(0, (boxWidth - visibleLength(text)) / 2);
        int rightPad = Math.max(0, boxWidth - visibleLength(text) - leftPad);
        printCentered(accent + "║" + reset + " ".repeat(leftPad) + text + " ".repeat(rightPad) + accent + "║" + reset);
    }

    private void printBox(String title, String[] lines, String accent, String textColor) {
        printBorderTop();
        if(title != null && !title.isEmpty()) {
            printTitle(title, accent);
            printDivider(accent);
        }
        for(String line : lines) {
            printRow(textColor + line + reset, accent);
        }
        printBorderBottom();
        System.out.println();
    }

    private void simpleBox(String title, String content, String accent, String textColor) {
        printBox(title, new String[]{content}, accent, textColor);
    }

    private String prompt(String label) {
        return center(bold + gold + "» " + label + reset);
    }

    private String tag(String label, String color) {
        return color + bold + label + reset;
    }

    private String bar(int value, int max, int length, String color) {
        int filled = Math.max(0, Math.min(length, (value * length) / max));
        StringBuilder out = new StringBuilder(color + "▐");
        for(int i = 0; i < length; i++) {
            out.append(i < filled ? "█" : "░");
        }
        out.append("▌").append(reset);
        return out.toString();
    }

    public void printWelcome() {
        System.out.println();
        printBorderTop();
        printRow("", cyan);
        printRow(bold + cyan + "🚀  S P A C E   W A R S  🚀" + reset, cyan);
        printRow(violet + "Galactic Combat Simulator" + reset, cyan);
        printRow(gray + "Command deck online. Battle systems primed." + reset, cyan);
        printRow("", cyan);
        printRow(gold + "Nebula glow  •  Plasma steel  •  Tactical chaos" + reset, cyan);
        printRow(white + "Welcome, Commander. Your duel begins now." + reset, cyan);
        printRow("", cyan);
        printBorderBottom();
        System.out.println();
    }

    public void printGameOver(String winner) {
        System.out.println();
        if(winner.equals("Player")) {
            printBox("MISSION RESULT", new String[]{
                green + bold + "VICTORY SECURED" + reset,
                white + "Your flagship stands triumphant over the arena."
            }, mint, white);
        } else if(winner.equals("Quit")) {
            printBox("MISSION RESULT", new String[]{
                yellow + bold + "BATTLE INTERRUPTED" + reset,
                white + "Returning safely to the command deck."
            }, amber, white);
        } else {
            printBox("MISSION RESULT", new String[]{
                red + bold + "DEFEAT CONFIRMED" + reset,
                white + "The enemy seized control of the battlefield."
            }, rose, white);
        }
        if(!winner.equals("Quit")) {
            simpleBox("WINNER", cyan + "Winner: " + white + winner, blue, white);
        }
    }

    public void printVictory() {
        simpleBox("VICTORY", green + "You defeated the enemy fleet.", mint, white);
    }

    public void printDefeat() {
        simpleBox("DEFEAT", red + "Your forces have fallen.", rose, white);
    }

    public int printInitialMusicMenu() {
        String[] lines = {
            silver + "Choose the mood for your battle bridge:",
            "",
            green + "  [1]  " + white + "Cinematic soundtrack",
            red + "  [2]  " + white + "Silent mode"
        };
        printBox("AUDIO SETUP", lines, violet, white);
        System.out.print(prompt("Choice: "));
        return validator.validatorChoiceMenu(1, 2);
    }

    public void printMusicToggled(boolean nowOn) {
        if(nowOn) {
            simpleBox("AUDIO", mint + "Background music is now ON", teal, white);
        } else {
            simpleBox("AUDIO", amber + "Background music is now OFF", rose, white);
        }
    }

    public int printMainMenu(boolean musicOn) {
        String[] lines = {
            cyan + "  [1]  " + white + "Launch New Battle",
            gold + "  [2]  " + white + "Load Saved Game",
            violet + "  [3]  " + white + "Continue Last Save",
            "",
            (musicOn ? rose : mint) + "  [0]  " + white + (musicOn ? "Turn Music OFF" : "Turn Music ON"),
            red + "  [9]  " + white + "Exit Game"
        };
        printBox("COMMAND CONSOLE", lines, blue, white);
        System.out.print(prompt("Choice: "));
        return validator.validatorChoiceMenu(0, 9);
    }

    public void printAttackMenu() {
        String[] lines = {
            mint + "  [1]  " + white + "Standard Attack  ⚔",
            gold + "  [2]  " + white + "Heavy Attack     💥",
            "",
            azure + "  [6]  " + white + "Pause Battle     ⏸",
            violet + "  [8]  " + white + "Save and Quit    💾",
            magenta + "  [9]  " + white + "Save Game        📁",
            rose + "  [7]  " + white + "Quit Without Save",
            amber + "  [0]  " + white + "Toggle Music     ♪"
        };
        printBox("TACTICAL ACTIONS", lines, rose, white);
        System.out.print(prompt("Choice: "));
    }

    public int printWarriorSelectionMenu() {
        int choice = 0;
        while(choice == 0) {
            String[] lines = {
                cyan + "  [1]  " + white + "Human Marine",
                mint + "  [2]  " + white + "Space Elf",
                rose + "  [3]  " + white + "Orc Brute"
            };
            printBox("SELECT YOUR CHAMPION", lines, cyan, white);
            System.out.print(prompt("Choice: "));
            choice = validator.validatorChoiceMenu(1, 3);
        }
        String type = choice == 1 ? "Human Marine" : (choice == 2 ? "Space Elf" : "Orc Brute");
        simpleBox("CHAMPION LOCKED", gold + "Selected: " + white + type, gold, white);
        return choice;
    }

    public void printArmourSelectionMenu() {
        String[] lines = {
            cyan + "  [1]  " + white + "Energy Shield",
            violet + "  [2]  " + white + "Nano-Suit",
            gold + "  [3]  " + white + "Plasma Armour"
        };
        printBox("SELECT ARMOUR", lines, violet, white);
        System.out.print(prompt("Choice: "));
    }

    public void printWeaponSelectionMenu() {
        String[] lines = {
            cyan + "  [1]  " + white + "Laser Pistol",
            blue + "  [2]  " + white + "Ion Sword",
            rose + "  [3]  " + white + "Plasma Axe"
        };
        printBox("SELECT WEAPON", lines, magenta, white);
        System.out.print(prompt("Choice: "));
    }

    public int printPlayAgainMenu(boolean musicOn) {
        String[] lines = {
            mint + "  [1]  " + white + "Play Again",
            rose + "  [2]  " + white + "Return to Desktop",
            "",
            (musicOn ? amber : mint) + "  [0]  " + white + (musicOn ? "Turn Music OFF" : "Turn Music ON")
        };
        printBox("ANOTHER BATTLE", lines, cyan, white);
        System.out.print(prompt("Choice: "));
        return validator.validatorChoiceMenu(0, 2);
    }

    public void printRoundStart(int round) {
        simpleBox("ROUND STATUS", gold + "🛰  Round " + round + " is underway", gold, white);
    }

    public void printHealthStatus(int playerHealth, int enemyHealth) {
        String playerColor = playerHealth < 90 ? red : (playerHealth < 180 ? yellow : mint);
        String enemyColor = enemyHealth < 90 ? red : (enemyHealth < 180 ? yellow : rose);
        String[] lines = {
            tag("YOU   ", cyan) + " " + bar(playerHealth, 420, 24, playerColor) + " " + white + playerHealth + " HP",
            tag("ENEMY ", rose) + " " + bar(enemyHealth, 420, 24, enemyColor) + " " + white + enemyHealth + " HP"
        };
        printBox("VITAL SIGNS", lines, blue, white);
        if(playerHealth < 90 || enemyHealth < 90) {
            String warning;
            if(playerHealth < 90 && enemyHealth < 90) {
                warning = red + bold + "Both fighters are entering critical condition." + reset;
            } else if(playerHealth < 90) {
                warning = amber + bold + "Warning: your ship integrity is critical." + reset;
            } else {
                warning = mint + bold + "Enemy hull is failing. Press the advantage." + reset;
            }
            simpleBox("ALERT", warning, amber, white);
        }
    }

    public void printPlayerTurn() {
        simpleBox("TURN", mint + "🎮 Your move", teal, white);
    }

    public void printEnemyTurn() {
        simpleBox("TURN", rose + "🤖 Enemy move", rose, white);
    }

    public void printPauseGame() {
        printBox("PAUSED", new String[]{
            silver + "Battle clock suspended.",
            white + "Press ENTER to return to combat."
        }, azure, white);
    }

    public void printHeavyWindup(String windup) {
        simpleBox("HEAVY ATTACK", gold + windup, gold, white);
    }

    public void printAttackReport(String attacker, String target, boolean heavyAttack, boolean didHit,
        int damageDone, int dexterityCost, int healthRemaining, String desc, boolean isPlayerAttack) {
        String accent = isPlayerAttack ? teal : rose;
        String resultText = didHit ? mint + "HIT" : amber + "MISS";
        String attackText = heavyAttack ? gold + "Heavy" : cyan + "Standard";
        String healthLabel = target.equals("Enemy") ? "Enemy HP left" : "Your HP left";
        
        String[] lines = {
            white + "Attacker: " + (isPlayerAttack ? cyan : rose) + attacker + reset + white + "   Target: " + (isPlayerAttack ? rose : cyan) + target,
            white + "Attack:   " + attackText + reset + white + "   Result: " + resultText,
            white + "Detail:   " + silver + desc,
            white + "Damage:   " + (didHit ? yellow + damageDone : gray + "0") + reset + white + "   Dex Cost: " + (dexterityCost > 0 ? amber + dexterityCost : gray + "0"),
            white + healthLabel + ": " + (healthRemaining > 0 ? green + healthRemaining : red + "0") + reset
        };
        printBox("ATTACK REPORT", lines, accent, white);
    }

    public void printPlayerHit(int damage, int health, String desc) {
        printBox("ATTACK RESULT", new String[]{
            mint + desc,
            yellow + "Damage dealt: " + damage,
            white + "Enemy health: " + health + " HP"
        }, teal, white);
    }

    public void printPlayerMiss(String desc) {
        printBox("ATTACK RESULT", new String[]{
            amber + desc,
            white + "No damage dealt."
        }, amber, white);
    }

    public void printEnemyHit(int damage, int health, String desc) {
        printBox("ATTACK RESULT", new String[]{
            rose + desc,
            yellow + "Damage taken: " + damage,
            white + "Your health: " + health + " HP"
        }, rose, white);
    }

    public void printEnemyMiss(String desc) {
        printBox("ATTACK RESULT", new String[]{
            mint + desc,
            white + "No damage taken."
        }, teal, white);
    }

    public void printStats(Warrior p, Armour pa, Weapon pw, Warrior e, Armour ea, Weapon ew, Arena a) {
        printBox("PLAYER PROFILE", new String[]{
            cyan + "Class      " + white + p.getClass().getSimpleName(),
            cyan + "Health     " + white + p.getHealth() + " HP",
            cyan + "Strength   " + white + p.getStrength(),
            cyan + "Dexterity  " + white + p.getDexterity(),
            cyan + "Armour     " + white + pa.getArmourType(),
            cyan + "Weapon     " + white + pw.getWeaponType()
        }, cyan, white);

        printBox("ENEMY PROFILE", new String[]{
            rose + "Class      " + white + e.getClass().getSimpleName(),
            rose + "Health     " + white + e.getHealth() + " HP",
            rose + "Strength   " + white + e.getStrength(),
            rose + "Dexterity  " + white + e.getDexterity(),
            rose + "Armour     " + white + ea.getArmourType(),
            rose + "Weapon     " + white + ew.getWeaponType()
        }, rose, white);

        if(a != null) {
            a.printArenaInfo();
        }
    }

    public void printSeparator() {
        printCentered(charcoal + "─".repeat(boxWidth + 4) + reset);
    }

    public void printNoSaveFound() {
        simpleBox("LOAD STATUS", amber + "No saved file found. A new battle will begin.", amber, white);
    }

    public void printSavePrompt() {
        System.out.print(prompt("Save this victory? (1=Yes, 2=No): "));
    }

    public void printGameLoaded(int round) {
        simpleBox("LOAD STATUS", mint + "Save restored. Resuming at round " + round, teal, white);
    }

    public void printResetMessage() {
        simpleBox("RESET", cyan + "Systems refreshed. New battle ready.", blue, white);
    }

    public void printBalancingMessage() {
        simpleBox("BATTLE SURGE", gold + "⚡ The fight intensifies. Damage output is rising.", gold, white);
    }

    public void printThanks() {
        printBox("SIGN OFF", new String[]{
            cyan + "Thanks for playing Space Wars.",
        }, blue, white);
    }

    public void printGameSaved() {
        simpleBox("SAVE STATUS", mint + "Game saved successfully.", teal, white);
    }

    public void printSaveAndQuit() {
        simpleBox("SAVE STATUS", gold + "Game saved. Returning to the command console.", gold, white);
    }

    public void printQuitNoSave() {
        simpleBox("EXIT STATUS", amber + "Leaving battle without saving.", rose, white);
    }

    public void printExitGame() {
        simpleBox("EXIT", gold + "Shutting down battle systems.", gold, white);
    }
}
