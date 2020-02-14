package main;

import main.game.GalgelogikI;
import main.game.GalgelogikImpl;

import java.io.IOException;
import java.rmi.Naming;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.rmi.server.hostname", "dist.saluton.dk");
        GalgelogikI logic = new GalgelogikImpl();
        java.rmi.registry.LocateRegistry.createRegistry(20123);
        Naming.rebind("rmi://dist.saluton.dk:20123/logics185123", logic);
        System.out.println("Gallows logic has been published with RMI");
    }
}
