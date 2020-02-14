package main.login;

import brugerautorisation.transport.rmi.Brugeradmin;

import java.rmi.Naming;
import java.util.Scanner;

public class Brugerlogin {
    public static boolean login(String username) throws Exception {
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        Scanner sc = new Scanner(System.in);
        String password;
        System.out.print("Enter password: ");
        for (int i = 0; i < 3; i++) {
            password = sc.nextLine();
            try {
                ba.hentBruger(username, password);
                return true;
            } catch (Exception e) {
                System.out.print("Wrong password\nTry again: ");
            }
        }
        return false;
    }
}
