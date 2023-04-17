package client.client_simple;

import client.ClientModel;

public class ClientSimpleLauncher {
    public static void main(String[] args) {
        ClientModel modele;
        ClientSimple vue;
        try {
            modele = new ClientModel();
            vue = new ClientSimple(modele);
            vue.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
