package main;


import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import main.game.BenytGalgelegModServer;
import main.game.GalgelogikI;
import main.login.Brugerlogin;

import java.rmi.Naming;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {



        String username, password;
        boolean loggedIn = false,failed = false, playagain = false;
        Scanner scan = new Scanner(System.in);
        //TODO login


        System.out.println("--- Velkommen til galgelegs spillet! Log ind for at fortsætte ---");

        do{
            if(failed)
                System.out.println("Brugernavn eller kodeord er forkert. Prøv igen!");

            System.out.print("Brugernavn:");
            username = scan.nextLine();
            System.out.print("Kodeord:");
            password = scan.nextLine();

            if(username.matches("test") && password.matches("test")) {
                loggedIn = true;
            } else {
                failed = true;
            }
        }while(!loggedIn);

        JSONObject spillet;

        do{


            System.out.println("Velkommen til Galgeleg!");

            boolean erSpilletSlut = false;
            String bogstavGættet;


            do {
                spillet = Unirest.get("").asJson().getBody().getObject(); //  <--indsæt URL
                System.out.println();
                System.out.println("--- Top of the round ---");
                System.out.println("Ordet du skal gætte er: "+ spillet.get("synligtord")); //indsæt synligt ord!
                System.out.println("Du har gættet på: ");

                String gættedeBogstaver[] = (String[]) spillet.get("usedLetters"); // Print alle gættede bogstaver

                for (int i = 0; i < gættedeBogstaver.length; i++){
                    System.out.print(gættedeBogstaver[i]);
                }

                System.out.println();

                art(0); //TODO GET LIV

                System.out.print("Gæt på et bogstav: ");

                do {

                    bogstavGættet = scan.next();

                    bogstavGættet.trim().toLowerCase();

                }while(bogstavGættet.matches("")||bogstavGættet.matches(" "));

                Unirest.post("localhost:8080/game").body(bogstavGættet).asJson();

                System.out.println("--- Round end ---");

            }while(erSpilletSlut);

            System.out.println("Spillet er slut. Vil du spille igen?");
            System.out.println("yes/no?:");
            if(scan.next().trim().toLowerCase().matches("yes")){

                playagain = false;
                //TODO play again

            } else {

                playagain = false;

            }

        }while (playagain);



    }

    private static void art(int liv){

        if (liv == 6){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 5){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 4){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("    |     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 3){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 2){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|\\    |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 1){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|\\    |    ");
            System.out.println("   /      |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 0){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|\\    |    ");
            System.out.println("   / \\    |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }

    }

}
