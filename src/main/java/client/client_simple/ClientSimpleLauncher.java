package client.client_simple;

import client.Client;
import server.Server;

public class ClientSimpleLauncher {
    public static void main(String[] args) {
        Client modele;
        ClientSimple vue;
        try {
            modele = new Client();
            vue = new ClientSimple(modele);
            vue.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
