package client.client_fx;

import client.ClientModel;

import java.util.ArrayList;

public class ClientFXController {
    private ClientModel modele;
    private ClientFXView vue;

    public ClientFXController(ClientModel m, ClientFXView v) {
        this.modele = m;
        this.vue = v;

        this.vue.getChargerButton().setOnAction((action) -> {
            this.charger();
        });

        this.vue.getEnvoyerButton().setOnAction((action) -> {
            this.envoyer();
        });

        this.vue.getDubButton().setOnAction((action) -> {
            this.dub();
        });

        this.vue.getDivButton().setOnAction((action) -> {
            this.div();
        });
    }

    private void charger() {
        this.modele.ajouter(1);
        this.vue.updateText(String.valueOf(this.modele.getValeur()));
    }

    private void envoyer() {
        String prenom = this.vue.getTextInfo("prenom");
        String nom = this.vue.getTextInfo("nom");
        String email = this.vue.getTextInfo("email");
        String matricule = this.vue.getTextInfo("matricule");
        String codeCours = this.vue.getSelectedCours();
        this.modele.validateRegistration(prenom, nom, email, matricule, codeCours);
        this.vue.updateText(String.valueOf(this.modele.getValeur()));
    }

    private void dub() {
        this.modele.multiplier(2);
        this.vue.updateText(String.valueOf(this.modele.getValeur()));
    }

    private void div() {
        this.modele.diviser(2);
        this.vue.updateText(String.valueOf(this.modele.getValeur()));
    }


}
