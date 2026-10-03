
package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validator {
    private static final Scanner input = new Scanner(System.in);
    
    public Validator() {}
    
    public int validatorChoiceMenu(int min, int max) {
        int choice = 0;
        
        while(choice == 0) {
            try {
                choice = Integer.parseInt(input.nextLine().trim());
                if(choice >= min && choice <= max) {
                    return choice;
                }
                else {
                    System.out.printf("Enter a number between %d and %d%n", min, max);
                    choice = 0;
                }
            }
            catch(NumberFormatException | InputMismatchException e) {
                System.out.printf("Enter a number between %d and %d%n", min, max);
                choice = 0;
            }
        } // while
        
        return choice;
    } // validatorChoiceMenu()
    
    public void waitForEnter() {
        input.nextLine();
    } // waitForEnter()
} // class
