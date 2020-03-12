package main.game;

import java.util.ArrayList;

public class GameState {

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getOrdet() {
        return ordet;
    }

    public void setOrdet(String ordet) {
        this.ordet = ordet;
    }

    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public void setBrugteBogstaver(ArrayList<String> brugteBogstaver) {
        this.brugteBogstaver = brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public void setSynligtOrd(String synligtOrd) {
        this.synligtOrd = synligtOrd;
    }

    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }

    public void setAntalForkerteBogstaver(int antalForkerteBogstaver) {
        this.antalForkerteBogstaver = antalForkerteBogstaver;
    }

    public boolean isSidsteBogstavVarKorrekt() {
        return sidsteBogstavVarKorrekt;
    }

    public void setSidsteBogstavVarKorrekt(boolean sidsteBogstavVarKorrekt) {
        this.sidsteBogstavVarKorrekt = sidsteBogstavVarKorrekt;
    }

    public boolean isSpilletErVundet() {
        return spilletErVundet;
    }

    public void setSpilletErVundet(boolean spilletErVundet) {
        this.spilletErVundet = spilletErVundet;
    }

    public boolean isSpilletErTabt() {
        return spilletErTabt;
    }

    public void setSpilletErTabt(boolean spilletErTabt) {
        this.spilletErTabt = spilletErTabt;
    }

    private String ordet;
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();
    private String synligtOrd;
    private int antalForkerteBogstaver = 0;
    private boolean sidsteBogstavVarKorrekt = false;
    private boolean spilletErVundet = false;
    private boolean spilletErTabt = false;
    private String user;
}
