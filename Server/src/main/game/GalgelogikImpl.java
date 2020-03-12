package main.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GalgelogikImpl extends UnicastRemoteObject implements GalgelogikI {
  /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
  ArrayList<String> muligeOrd = new ArrayList<String>();
  HashMap<String, GameState> games = new HashMap<>();

  public GalgelogikImpl() throws java.rmi.RemoteException {
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    muligeOrd.add("nitten");
  }


  @Override
  public ArrayList<String> getBrugteBogstaver(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.getBrugteBogstaver();
  }

  @Override
  public String getSynligtOrd(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.getSynligtOrd();
  }

  @Override
  public String getOrdet(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.getOrdet();
  }

  @Override
  public int getAntalForkerteBogstaver(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.getAntalForkerteBogstaver();
  }

  @Override
  public boolean erSidsteBogstavKorrekt(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.isSidsteBogstavVarKorrekt();
  }

  @Override
  public boolean erSpilletVundet(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.isSpilletErVundet();
  }

  @Override
  public boolean erSpilletTabt(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.isSpilletErTabt();
  }

  @Override
  public boolean erSpilletSlut(String user) {
    userExists(user);
    GameState game = games.get(user);
    return game.isSpilletErTabt()|| game.isSpilletErVundet();
  }


  @Override
  public void nulstil(String user) {
    games.remove(user);
  }


  private void opdaterSynligtOrd(String user) {
    userExists(user);
    GameState game = games.get(user);
    game.setSynligtOrd("");
    game.setSpilletErVundet(true);
    for (int n = 0; n < game.getOrdet().length(); n++) {
      String bogstav = game.getOrdet().substring(n, n + 1);
      if (game.getBrugteBogstaver().contains(bogstav)) {
        game.setSynligtOrd(game.getSynligtOrd() + bogstav);
      } else {
        game.setSynligtOrd(game.getSynligtOrd() + "*");
        game.setSpilletErVundet(false);
      }
    }
  }

  @Override
  public void gætBogstav(String user, String bogstav) {
    userExists(user);
    GameState game = games.get(user);
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (game.getBrugteBogstaver().contains(bogstav)) return;
    if (game.isSpilletErVundet() || game.isSpilletErTabt()) return;

    game.getBrugteBogstaver().add(bogstav);

    if (game.getOrdet().contains(bogstav)) {
      game.setSidsteBogstavVarKorrekt(true);
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      game.setSidsteBogstavVarKorrekt(false);
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      game.setAntalForkerteBogstaver(game.getAntalForkerteBogstaver() + 1);
      if (game.getAntalForkerteBogstaver() > 6) {
        game.setSpilletErTabt(true);
      }
    }
    opdaterSynligtOrd(user);
  }

  @Override
  public void logStatus(String user) {
    userExists(user);
    GameState game = games.get(user);

    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + game.getOrdet());
    System.out.println("- synligtOrd = " + game.getSynligtOrd());
    System.out.println("- forkerteBogstaver = " + game.getAntalForkerteBogstaver());
    System.out.println("- brugeBogstaver = " + game.getBrugteBogstaver());
    if (game.isSpilletErTabt()) System.out.println("- SPILLET ER TABT");
    if (game.isSpilletErVundet()) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    System.out.println("Henter data fra " + url);
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }


  /**
   * Hent ord fra DRs forside (https://dr.dk)
   */
  @Override
  public void hentOrdFraDr() throws Exception {
    String data = hentUrl("https://dr.dk");
    //System.out.println("data = " + data);

    data = data.substring(data.indexOf("<body")). // fjern headere
            replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
            replaceAll("&#198;", "æ"). // erstat HTML-tegn
            replaceAll("&#230;", "æ"). // erstat HTML-tegn
            replaceAll("&#216;", "ø"). // erstat HTML-tegn
            replaceAll("&#248;", "ø"). // erstat HTML-tegn
            replaceAll("&oslash;", "ø"). // erstat HTML-tegn
            replaceAll("&#229;", "å"). // erstat HTML-tegn
            replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
            replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
            replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

    System.out.println("data = " + data);
    System.out.println("data = " + Arrays.asList(data.split("\\s+")));
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
  }


  /**
   * Hent ord og sværhedsgrad fra et online regneark. Du kan redigere i regnearket, på adressen
   * https://docs.google.com/spreadsheets/d/1RnwU9KATJB94Rhr7nurvjxfg09wAHMZPYB3uySBPO6M/edit?usp=sharing
   * @param sværhedsgrader en streng med de tilladte sværhedsgrader - f.eks "3" for at medtage kun svære ord, eller "12" for alle nemme og halvsvære ord
   * @throws Exception
   */

  @Override
  public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {
    String id = "1RnwU9KATJB94Rhr7nurvjxfg09wAHMZPYB3uySBPO6M";

    System.out.println("Henter data som kommasepareret CSV fra regnearket https://docs.google.com/spreadsheets/d/"+id+"/edit?usp=sharing");

    String data = hentUrl("https://docs.google.com/spreadsheets/d/" + id + "/export?format=csv&id=" + id);
    int linjeNr = 0;

    muligeOrd.clear();
    for (String linje : data.split("\n")) {
      if (linjeNr<20) System.out.println("Læst linje = " + linje); // udskriv de første 20 linjer
      if (linjeNr++ < 1 ) continue; // Spring første linje med kolonnenavnene over
      String[] felter = linje.split(",", -1);// -1 er for at beholde tomme indgange, f.eks. bliver ",,," splittet i et array med 4 tomme strenge
      String sværhedsgrad = felter[0].trim();
      String ordet = felter[1].trim().toLowerCase();
      if (sværhedsgrad.isEmpty() || ordet.isEmpty()) continue; // spring over linjer med tomme ord
      if (!sværhedsgrader.contains(sværhedsgrad)) continue; // filtrér på sværhedsgrader
      System.out.println("Tilføjer "+ordet+", der har sværhedsgrad "+sværhedsgrad);
      muligeOrd.add(ordet);
    }

    System.out.println("muligeOrd = " + muligeOrd);
  }

  private void userExists(String user) {
    if (games.containsKey(user))
      return;
    GameState game = new GameState();
    game.setUser(user);
    game.setOrdet(muligeOrd.get(new Random(System.currentTimeMillis()).nextInt(muligeOrd.size())));
    int wordLength = game.getOrdet().length();
    String visible = "";
    for (int i = 0; i < wordLength; i++) {
      visible += "*";
    }
    game.setSynligtOrd(visible);
    games.put(user, game);
  }
}
