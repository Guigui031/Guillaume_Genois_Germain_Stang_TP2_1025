package client.client_fx;

import client.ClientModel;

import java.io.IOException;
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

    }

    private void charger() {
        String session = this.vue.getSession();
        try {
            this.modele.handleCourseRequest(session);
            this.vue.updateListView(this.modele.getCours());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void envoyer() {
        String prenom = this.vue.getTextInfo("prenom");
        String nom = this.vue.getTextInfo("nom");
        String email = this.vue.getTextInfo("email");
        String matricule = this.vue.getTextInfo("matricule");
        String codeCours = this.modele.getCours().get(this.vue.getSelectedCours()).getCode();
        this.modele.validateRegistration(prenom, nom, email, matricule, codeCours);

    }

    private void dub() {

    }

    private void div() {

    }


}
