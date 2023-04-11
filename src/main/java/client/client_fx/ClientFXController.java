package client.client_fx;

import client.ClientModel;

public class ClientFXController {
    private ClientModel modele;
    private ClientFXView vue;

    public ClientFXController(ClientModel m, ClientFXView v) {
        this.modele = m;
        this.vue = v;
    }


}
