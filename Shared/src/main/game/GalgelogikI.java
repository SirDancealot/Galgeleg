package main.game;

import java.util.ArrayList;

public interface GalgelogikI extends java.rmi.Remote{
    ArrayList<String> getBrugteBogstaver(String user) throws java.rmi.RemoteException;

    String getSynligtOrd(String user) throws java.rmi.RemoteException;

    String getOrdet(String user) throws java.rmi.RemoteException;

    int getAntalForkerteBogstaver(String user) throws java.rmi.RemoteException;

    boolean erSidsteBogstavKorrekt(String user) throws java.rmi.RemoteException;

    boolean erSpilletVundet(String user) throws java.rmi.RemoteException;

    boolean erSpilletTabt(String user) throws java.rmi.RemoteException;

    boolean erSpilletSlut(String user) throws java.rmi.RemoteException;

    void nulstil(String user) throws java.rmi.RemoteException;

    void gætBogstav(String user, String bogstav) throws java.rmi.RemoteException;

    void logStatus(String user) throws java.rmi.RemoteException;

    void hentOrdFraDr() throws Exception, java.rmi.RemoteException;

    void hentOrdFraRegneark(String sværhedsgrader) throws Exception, java.rmi.RemoteException;
}
