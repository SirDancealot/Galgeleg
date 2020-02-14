package main;


import main.game.BenytGalgelegModServer;
import main.game.GalgelogikI;
import main.login.Brugerlogin;

import java.rmi.Naming;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        boolean loggedIn = false;
        do {
            System.out.print("Enter username (Empty to exit): ");
            String username = sc.nextLine().trim();

            if (username.isEmpty())
                System.exit(0);

            loggedIn = Brugerlogin.login(username);
        } while (!loggedIn);

        System.out.println("Logged in");

        boolean playing = true;

        do {
            BenytGalgelegModServer.play();
            System.out.print("Do you want to play again (Y/n)? ");
            String answer = sc.nextLine().trim().toLowerCase();

            if (!(answer.equals("y") || answer.equals("yes") || answer.isEmpty())) {
                playing = false;
            }
        } while (playing);

        System.out.println("Thanks for playing");
    }
}
