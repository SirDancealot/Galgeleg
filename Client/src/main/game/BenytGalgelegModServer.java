package main.game;

import java.rmi.Naming;
import java.util.Scanner;

public class BenytGalgelegModServer {
    public static boolean play() throws  Exception{
        GalgelogikI logic = (GalgelogikI) Naming.lookup("rmi://dist.saluton.dk:20123/logics185123");
        Scanner sc = new Scanner(System.in);

        logic.nulstil();

        while (!logic.erSpilletSlut()) {
            int wrong = logic.getAntalForkerteBogstaver();
            System.out.println("You have guessed wrong " + wrong + " out of 7 times");
            if (wrong == 6)
                System.out.println("If you guess wrong again you will lose");
            System.out.println("Your currently visible word is:");
            System.out.println(logic.getSynligtOrd());
            System.out.print("Guess a letter: ");
            String guess = sc.nextLine().trim().toLowerCase();
            if (guess.length() > 1 || guess.isEmpty()) {
                System.out.println( guess.isEmpty() ? "You have to write a letter" : "You can only write one letter" );
            } else {
                logic.gætBogstav(guess);
                System.out.println(logic.erSidsteBogstavKorrekt() ? "You guessed correctly" : "That was sadly not a correct letter");
            }
            System.out.println("-------------------------------------------");
        }

        System.out.println(logic.erSpilletVundet() ? "Concratulations you won" : "You sadly lost");
        System.out.println("The word was '" + logic.getOrdet() + "'");
        return logic.erSpilletVundet();
    }
}
