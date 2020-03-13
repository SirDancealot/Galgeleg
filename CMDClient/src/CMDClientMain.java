import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class CMDClientMain {

    private static String username;

    public static void main(String[] args) throws Exception {

        String URL = "http://dist.saluton.dk:20129";
        boolean loggedIn = false;
        boolean playAgain;
        Scanner scan = new Scanner(System.in);
        JSONObject spilInformation;
        ArrayList<String> brugteBogstaver = new ArrayList<>();
        String bogstav;

        System.out.println("--- Velkommen til galgelegs spillet! Log ind for at fortsætte ---");

        do {
            if (login())
                loggedIn = true;
        }while (!loggedIn);

        do{

            validateUser();
            spilInformation = Unirest.get(URL+"/game/"+username).asJson().getBody().getObject();

            System.out.println("Velkommen til Galgeleg!");



            do {

                System.out.println();
                System.out.println("--- Top of the round ---");
                System.out.println("Ordet du skal gætte er: "+ spilInformation.getString("visibleWord"));
                System.out.println("Du har allerede gættet på: ");

                spilInformation.getJSONArray("usedLetters");

                for (int i = 0; i < spilInformation.getJSONArray("usedLetters").length(); i++){
                    brugteBogstaver.clear();
                    brugteBogstaver.add(spilInformation.getJSONArray("usedLetters").getString(i));
                    System.out.print(brugteBogstaver.toString());
                }

                System.out.println();

                art(spilInformation.getInt("lives"));

                System.out.print("Gæt på et bogstav: ");

                do {

                    bogstav = scan.next();

                    bogstav.trim().toLowerCase();

                }while(!(bogstav.matches("[a-z]")));

                validateUser();
                Unirest.post(URL+"/game").field("name",username).field("guess",bogstav).asEmpty();

                System.out.println("--- Round end ---");

                validateUser();
                spilInformation = Unirest.get(URL+"/game/"+username).asJson().getBody().getObject();

            } while (spilInformation.getString("isGameOver").equals("false"));

            if(spilInformation.getInt("lives") == 7){

                System.out.println("Du har tabt :^( den stakkels mand blev hængt");

            } else {
                System.out.println("Du har vundet, du havde " + (7-spilInformation.getInt("lives")) + " liv tilbage! :^)");
            }

            System.out.println("Spillet er slut. Vil du spille igen?");
            System.out.println("yes/no?:");

            if("yes".matches(scan.next().trim().toLowerCase())){
                validateUser();
                Unirest.delete(URL+"/game").field("name",username).asEmpty();
                playAgain = true;
            } else {
                validateUser();
                playAgain = false;
                Unirest.delete(URL+"/game").field("name",username).asEmpty();
                System.out.println("Goodbye");
            }

        } while (playAgain);

    }


    private static boolean login(){
        boolean loggedIn;

        Scanner scan = new Scanner(System.in);

        do{
            System.out.print("Brugernavn:");
            username = scan.nextLine();
            System.out.print("Kodeord:");
            String password = scan.nextLine();

            String URL = "http://localhost:20129";
            HttpResponse response = Unirest.post(URL +"/login").field("name",username).field("password", password).asEmpty();

            if(response.getStatus() == 200){
                loggedIn = true;
            } else {
                System.out.println("Brugernavn eller kodeord er forkert. Prøv igen!");
                loggedIn = false;
            }
        }while(!loggedIn);

        return true;
    }

    private static void validateUser(){
        HttpResponse httpResponse = Unirest.get("http://dist.saluton.dk:20129/login/"+username).asJson();
        if (httpResponse.getStatus() != 200){
            System.out.println("You have been logged out, please login again");
            login();
        }
    }

    private static void art(int liv){

        if (liv == 0){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 1){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 2){
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
        }else if(liv == 4){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|\\    |    ");
            System.out.println("          |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 5){
            System.out.println("               ");
            System.out.println("    -------    ");
            System.out.println("    |     |    ");
            System.out.println("    O     |    ");
            System.out.println("   /|\\    |    ");
            System.out.println("   /      |    ");
            System.out.println("          |    ");
            System.out.println("        -----  ");
        }else if(liv == 6){
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
