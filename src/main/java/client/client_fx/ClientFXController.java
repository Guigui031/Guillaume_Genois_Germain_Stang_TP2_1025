package client.client_fx;

import client.ClientModel;
import erreurs.EmailException;
import erreurs.InscriptionEchoueeException;
import erreurs.MauvaisChoixException;

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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
        }
    }

    private void envoyer() {
        try {
            String prenom = this.vue.getTextInfo("Prénom");
            String nom = this.vue.getTextInfo("Nom");
            String email = this.vue.getTextInfo("Email");
            String matricule = this.vue.getTextInfo("Matricule");
            String codeCours = this.modele.getCours().get(this.vue.getSelectedCours()).getCode();
            this.modele.validateRegistration(prenom, nom, email, matricule, codeCours);
        } catch (NullPointerException e) {
            vue.alert("Erreur. Le champ " + e.getMessage() + " est vide.");
        } catch (IOException e) {
            vue.alert("Erreur dans la connection au serveur. Veuiller vous assurez qu'il est actif.");
        } catch (MauvaisChoixException e) {
            vue.alert("Le choix \"" + e.getMessage() + "\" que vous avez effectué n'existe pas.");
        } catch (InscriptionEchoueeException e) {
            vue.alert(e.getMessage());
        } catch (ClassNotFoundException e) {
            vue.alert("La classe envoyée par le serveur n'existe pas dans le programme.");
        }


    }


    private boolean verifyEmail(String email) {
        try {
            modele.validateEmail(email);
            return true;
        } catch (EmailException e) {
            vue.alert("Mauvais email. Veuiller écrire un email valide.");
            return false;
        }
    }


}
