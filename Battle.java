
import util.*;
import warrior.*;
import armour.*;
import weapon.*;
import arena.Arena;

import java.security.SecureRandom;

public class Battle {
    private static Ink ink = new Ink();
    private static Validator validator = new Validator();
    private static SecureRandom random = new SecureRandom();
    private static PlayByPlay announcer = new PlayByPlay();
    private static GameSave saveSystem = new GameSave();
    private static AudioPlayer audio = new AudioPlayer();
    
    private static Warrior player;
    private static Armour playerArmour;
    private static Weapon playerWeapon;
    private static Warrior enemy;
    private static Armour enemyArmour;
    private static Weapon enemyWeapon;
    private static Arena currentArena;
    
    private static String victor;
    private static boolean playerTurn = true;
    private static boolean gameFinished = false;
    private static int currentRound = 1;
    private static int totalActions = 0;
    private static boolean musicEnabled = true;

    public static void main(String[] args) {
        ink.printWelcome();
        
        //===============>>
        // Music Setup
        int musicPref = ink.printInitialMusicMenu();
        if(musicPref == 1) {
            audio.playBackgroundMusic("sounds/space_battle.wav");
            musicEnabled = true;
        }
        else {
            musicEnabled = false;
            ink.printMusicToggled(false);
        }
        
        boolean keepPlaying = true;
        
        //=====================>>
        //=== Main Game Loop
        while(keepPlaying) {
            int menuChoice = ink.printMainMenu(musicEnabled);
            
            if(menuChoice == 0) {
                toggleMusic();
                continue;
            }
            
            if(menuChoice == 9) {
                ink.printExitGame();
                if(musicEnabled) {
                    audio.stopBackgroundMusic();
                }
                ink.printThanks();
                System.exit(0);
            }
            
            if(menuChoice == 3 || menuChoice == 2) {
                GameSave.SaveData savedData = saveSystem.loadGame();
                if(savedData != null) {
                    loadSavedGame(savedData);
                }
                else {
                    ink.printNoSaveFound();
                    startNewGame();
                }
            }
            else {
                startNewGame();
            }
            
            ink.printStats(player, playerArmour, playerWeapon, enemy, enemyArmour, enemyWeapon, currentArena);
            
            gameFinished = false;
            
            while(!gameFinished) {
                ink.printRoundStart(currentRound);
                ink.printHealthStatus(player.getHealth(), enemy.getHealth());
                ink.printSeparator();
                
                if(playerTurn) {
                    ink.printPlayerTurn();
                    ink.printAttackMenu();
                    
                    int choice = getValidNumber(0, 9);
                    
                    if(choice == 0) {
                        toggleMusic();
                        continue;
                    }
                    
                    if(choice == 9) {
                        saveSystem.saveGame(player, playerArmour, playerWeapon, enemy, enemyArmour, enemyWeapon, currentArena, currentRound);
                        ink.printGameSaved();
                        continue;
                    }
                    
                    if(choice == 8) {
                        saveSystem.saveGame(player, playerArmour, playerWeapon, enemy, enemyArmour, enemyWeapon, currentArena, currentRound);
                        ink.printGameSaved();
                        ink.printSaveAndQuit();
                        gameFinished = true;
                        victor = "Quit";
                        break;
                    }
                    
                    if(choice == 7) {
                        ink.printQuitNoSave();
                        gameFinished = true;
                        victor = "Quit";
                        break;
                    }
                    
                    if(choice == 6) {
                        ink.printPauseGame();
                        validator.waitForEnter();
                        continue;
                    }
                    
                    boolean heavyAttack = (choice == 2);
                    String weaponName = playerWeapon.getWeaponType();
                    Weapon.AttackResult attackResult = attackWithWeapon(playerWeapon, player, playerArmour, heavyAttack);
                    int damageDealt = attackResult.getDamageDone();
                    
                    if(attackResult.didHitTarget() && currentArena != null) {
                        damageDealt = currentArena.applyModifier(damageDealt, true);
                    }
                    
                    if(attackResult.didHitTarget()) {
                        int armourBlock = enemyArmour.getProtectionAmount() / 10;
                        damageDealt -= armourBlock;
                        if(damageDealt < 8) {
                            damageDealt = 8;
                        }
                        enemy.takeDamage(damageDealt);
                    }
                    
                    if(attackResult.didHitTarget()) {
                        String desc = announcer.getHitDescription(weaponName, damageDealt, true);
                        ink.printAttackReport("You", "Enemy", heavyAttack, true,
                            damageDealt, attackResult.getDexterityCost(), enemy.getHealth(), desc, true);
                        if(musicEnabled) {
                            audio.playSoundEffect("sounds/hit.wav");
                        }
                    }
                    else {
                        String desc;
                        if(!attackResult.hadEnoughDexterity()) {
                            desc = announcer.getNoDexterityDescription(weaponName, true);
                        }
                        else {
                            desc = announcer.getMissDescription(weaponName, true);
                        }
                        ink.printAttackReport("You", "Enemy", heavyAttack, false,
                            0, attackResult.getDexterityCost(), enemy.getHealth(), desc, true);
                        if(musicEnabled) {
                            audio.playSoundEffect("sounds/miss.wav");
                        }
                    }
                    
                    ink.printSeparator();
                    
                    if(enemy.getHealth() <= 0) {
                        gameFinished = true;
                        victor = "Player";
                        System.out.println("\n  ★ YOU DEFEATED THE ENEMY! ★");
                        break;
                    }
                    
                    playerTurn = false;
                    totalActions++;
                    
                } else {
                    ink.printEnemyTurn();
                    
                    int enemyChoice = random.nextInt(100);
                    boolean heavyAttack = (enemyChoice < 40);
                    String weaponName = enemyWeapon.getWeaponType();
                    Weapon.AttackResult attackResult = attackWithWeapon(enemyWeapon, enemy, enemyArmour, heavyAttack);
                    int damageDealt = attackResult.getDamageDone();
                    
                    if(attackResult.didHitTarget() && currentArena != null) {
                        damageDealt = currentArena.applyModifier(damageDealt, false);
                    }
                    
                    if(attackResult.didHitTarget()) {
                        int armourBlock = playerArmour.getProtectionAmount() / 10;
                        damageDealt -= armourBlock;
                        if(damageDealt < 8) {
                            damageDealt = 8;
                        }
                        player.takeDamage(damageDealt);
                    }
                    
                    if(attackResult.didHitTarget()) {
                        String desc = announcer.getHitDescription(weaponName, damageDealt, false);
                        ink.printAttackReport("Enemy", "You", heavyAttack, true,
                            damageDealt, attackResult.getDexterityCost(), player.getHealth(), desc, false);
                        if(musicEnabled) {
                            audio.playSoundEffect("sounds/hit.wav");
                        }
                    }
                    else {
                        String desc;
                        if(!attackResult.hadEnoughDexterity()) {
                            desc = announcer.getNoDexterityDescription(weaponName, false);
                        }
                        else {
                            desc = announcer.getMissDescription(weaponName, false);
                        }
                        ink.printAttackReport("Enemy", "You", heavyAttack, false,
                            0, attackResult.getDexterityCost(), player.getHealth(), desc, false);
                        if(musicEnabled) {
                            audio.playSoundEffect("sounds/miss.wav");
                        }
                    }
                    
                    ink.printSeparator();
                    
                    if(player.getHealth() <= 0) {
                        gameFinished = true;
                        victor = "Enemy";
                        System.out.println("\n  ★ YOU WERE DEFEATED! ★");
                        break;
                    }
                    
                    playerTurn = true;
                    currentRound++;
                    totalActions++;
                }
                
                if(totalActions >= 8 && !gameFinished && totalActions % 4 == 0) {
                    ink.printBalancingMessage();
                    applyBalanceBoost();
                }
                
                if(currentRound >= 15 && !gameFinished) {
                    gameFinished = true;
                    if(player.getHealth() > enemy.getHealth()) {
                        victor = "Player";
                    }
                    else if(enemy.getHealth() > player.getHealth()) {
                        victor = "Enemy";
                    }
                    else {
                        victor = random.nextBoolean() ? "Player" : "Enemy";
                    }
                }
            }
            
            if(!victor.equals("Quit")) {
                ink.printGameOver(victor);
            }
            
            if(victor.equals("Player")) {
                ink.printSavePrompt();
                int saveChoice = getValidNumber(1, 2);
                if(saveChoice == 1) {
                    saveSystem.saveGame(player, playerArmour, playerWeapon, enemy, enemyArmour, enemyWeapon, currentArena, currentRound);
                    ink.printGameSaved();
                }
            }
            
            if(!victor.equals("Quit")) {
                int againChoice = ink.printPlayAgainMenu(musicEnabled);
                if(againChoice == 0) {
                    toggleMusic();
                    keepPlaying = true;
                }
                else if(againChoice == 1) {
                    keepPlaying = true;
                }
                else {
                    keepPlaying = false;
                }
            }
            else {
                keepPlaying = true;
            }
        } // while
        
        if(musicEnabled) {
            audio.stopBackgroundMusic();
        }
        ink.printThanks();
    } // main
    
    private static int getValidNumber(int min, int max) {
        return validator.validatorChoiceMenu(min, max);
    } // getValidNumber()
    
    private static Weapon.AttackResult attackWithWeapon(Weapon weapon, Warrior warrior,
        Armour armour, boolean heavyAttack) {
        if(weapon instanceof Dagger) {
            return ((Dagger)weapon).attack(warrior, weapon, armour, heavyAttack);
        }
        else if(weapon instanceof Sword) {
            return ((Sword)weapon).attack(warrior, weapon, armour, heavyAttack);
        }
        else {
            return ((Axe)weapon).attack(warrior, weapon, armour, heavyAttack);
        }
    } // attackWithWeapon()
    
    private static void toggleMusic() {
        if(musicEnabled) {
            audio.stopBackgroundMusic();
            musicEnabled = false;
        }
        else {
            audio.playBackgroundMusic("sounds/space_battle.wav");
            musicEnabled = true;
        }
        ink.printMusicToggled(musicEnabled);
    } // toggleMusic()
    
    private static void applyBalanceBoost() {
        if(player.getHealth() > 0 && enemy.getHealth() > 0) {
            int boost = currentRound >= 10 ? 18 : 12;
            player.setStrength(player.getStrength() + boost);
            enemy.setStrength(enemy.getStrength() + boost);
        }
    } // applyBalanceBoost()
    
    private static void loadSavedGame(GameSave.SaveData data) {
        resetGame();
        playerTurn = true;
        
        // Create player
        if(data.playerType == 1) {
            player = new Human();
        } else if(data.playerType == 2) {
            player = new Elf();
        } else {
            player = new Orc();
        }
        player.setHealth(data.playerHealth);
        player.setStrength(data.playerStrength);
        player.setDexterity(data.playerDexterity);
        
        // Player armour
        if(data.playerArmour == 1) {
            playerArmour = new Leather();
        } else if(data.playerArmour == 2) {
            playerArmour = new Chainmail();
        } else {
            playerArmour = new Platemail();
        }
        
        // Player weapon
        if(data.playerWeapon == 1) {
            playerWeapon = new Dagger();
        } else if(data.playerWeapon == 2) {
            playerWeapon = new Sword();
        } else {
            playerWeapon = new Axe();
        }
        
        // Create enemy
        if(data.enemyType == 1) {
            enemy = new Human();
        } else if(data.enemyType == 2) {
            enemy = new Elf();
        } else {
            enemy = new Orc();
        }
        enemy.setHealth(data.enemyHealth);
        enemy.setStrength(data.enemyStrength);
        enemy.setDexterity(data.enemyDexterity);
        
        // Enemy armour
        if(data.enemyArmour == 1) {
            enemyArmour = new Leather();
        } else if(data.enemyArmour == 2) {
            enemyArmour = new Chainmail();
        } else {
            enemyArmour = new Platemail();
        }
        
        // Enemy weapon
        if(data.enemyWeapon == 1) {
            enemyWeapon = new Dagger();
        } else if(data.enemyWeapon == 2) {
            enemyWeapon = new Sword();
        } else {
            enemyWeapon = new Axe();
        }
        
        currentArena = new Arena(data.arenaName);
        currentRound = data.round;
        totalActions = (currentRound - 1) * 2;
        
        ink.printStats(player, playerArmour, playerWeapon, enemy, enemyArmour, enemyWeapon, currentArena);
        ink.printGameLoaded(currentRound);
    } // loadSavedGame()
    
    private static void startNewGame() {
        resetGame();
        setupPlayer();
        setupEnemy();
        currentArena = new Arena();
        playerTurn = random.nextBoolean();
    } // startNewGame()
    
    private static void resetGame() {
        player = null;
        playerArmour = null;
        playerWeapon = null;
        enemy = null;
        enemyArmour = null;
        enemyWeapon = null;
        currentArena = null;
        victor = null;
        playerTurn = true;
        gameFinished = false;
        currentRound = 1;
        totalActions = 0;
        ink.printResetMessage();
    } // resetGame()
    
    private static void setupPlayer() {
        playerTurn = true;
        
        int classChoice = ink.printWarriorSelectionMenu();
        if(classChoice == 1) {
            player = new Human();
        }
        else if(classChoice == 2) {
            player = new Elf();
        }
        else {
            player = new Orc();
        }
        
        ink.printArmourSelectionMenu();
        int armourChoice = getValidNumber(1, 3);
        if(armourChoice == 1) {
            playerArmour = new Leather();
        }
        else if(armourChoice == 2) {
            playerArmour = new Chainmail();
        }
        else {
            playerArmour = new Platemail();
        }
        
        ink.printWeaponSelectionMenu();
        int weaponChoice = getValidNumber(1, 3);
        if(weaponChoice == 1) {
            playerWeapon = new Dagger();
        }
        else if(weaponChoice == 2) {
            playerWeapon = new Sword();
        }
        else {
            playerWeapon = new Axe();
        }
    } // setupPlayer()
    
    private static void setupEnemy() {
        playerTurn = false;
        
        int classChoice = random.nextInt(3) + 1;
        if(classChoice == 1) {
            enemy = new Human();
        }
        else if(classChoice == 2) {
            enemy = new Elf();
        }
        else {
            enemy = new Orc();
        }
        
        int armourChoice = random.nextInt(3) + 1;
        if(armourChoice == 1) {
            enemyArmour = new Leather();
        }
        else if(armourChoice == 2) {
            enemyArmour = new Chainmail();
        }
        else {
            enemyArmour = new Platemail();
        }
        
        int weaponChoice = random.nextInt(3) + 1;
        if(weaponChoice == 1) {
            enemyWeapon = new Dagger();
        }
        else if(weaponChoice == 2) {
            enemyWeapon = new Sword();
        }
        else {
            enemyWeapon = new Axe();
        }
    } // setupEnemy()
} // class
