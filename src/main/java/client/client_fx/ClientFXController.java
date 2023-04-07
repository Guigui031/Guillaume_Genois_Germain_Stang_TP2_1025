package client.client_fx;

import client.ClientModel;

public class ClientFXController {
    private ClientModel modele;
    private ClientFXView vue;

    public ClientFXController(ClientModel m, ClientFXView v) {
        this.modele = m;
        this.vue = v;

        /*
         * La definition du comportement de chaque handler
         * est mise dans sa propre méthode auxiliaire. Il pourrait être même
         * dans sa propre classe entière: ne niveau de decouplage
         * depend de la complexité de l'application
         */


    }


}
