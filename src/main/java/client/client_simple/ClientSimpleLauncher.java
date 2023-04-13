package client.client_simple;

import client.ClientModel;

public class ClientSimpleLauncher {
    /**
     * Permet le lancement de l'application clientSimple.
     * @param args paramètre le lancement de l'application
     */
    public static void main(String[] args) {
        ClientModel modele;
        ClientSimple vue;

        modele = new ClientModel();
        vue = new ClientSimple(modele);
        vue.run();
        vue.close();

    }
}
