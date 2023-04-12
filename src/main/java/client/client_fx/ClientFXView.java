package client.client_fx;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import server.models.Course;

import java.util.ArrayList;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 * Notez que cette classe est completement independante de toute definition
 * de comportement.
 */
public class ClientFXView extends BorderPane {

    private ListView<String> listView = new ListView<>();  // TODO: default "No content in table"
    private Button chargerButton = new Button("charger");
    private ComboBox<String> sessionList = new ComboBox<>();

    private Button envoyerButton = new Button("envoyer");
    //private ArrayList<Text> infoNameList = new ArrayList<>();  // TODO: quelle méthode meilleure?
    //private ArrayList<TextField> infoFieldList = new ArrayList<>();
    private VBox infoBox = new VBox(10);


    public ClientFXView() {
        BorderPane root = new BorderPane();

        // Partie de droite inscription
        VBox inscriptionBox = initInscBox();

        // Partie de gauche liste des cours
        VBox listeCoursBox = initListeCoursBox();

        // Aligner les deux parties
        SplitPane splitPane = new SplitPane(listeCoursBox, inscriptionBox);
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.5);
        root.setCenter(splitPane);

        this.setCenter(root);
    }

    private VBox initInscBox() {
        // Partie de droite inscription
        VBox inscriptionBox = new VBox();
        Label inscriptionLabel = new Label("Formulaire d'inscription");
        inscriptionLabel.setFont(new Font("Arial", 25));

        //VBox infoBox = new VBox(10);
        HBox prenomBox = generateInfo("Prénom");
        HBox nomBox = generateInfo("Nom");
        HBox emailBox = generateInfo("Email");
        HBox matriculeBox = generateInfo("Matricule");

        infoBox.getChildren().addAll(prenomBox, nomBox, emailBox, matriculeBox);
        infoBox.setAlignment(Pos.CENTER);

        envoyerButton.setFont(new Font("Arial", 13));
        envoyerButton.setMinWidth(90);

        inscriptionBox.getChildren().addAll(inscriptionLabel, infoBox, envoyerButton);
        inscriptionBox.setAlignment(Pos.TOP_CENTER);
        inscriptionBox.setPadding(new Insets(15, 10, 0, 10));
        inscriptionBox.setSpacing(30);

        return inscriptionBox;
    }

    private HBox generateInfo(String info) {
        HBox box = new HBox();

        Text text = new Text(info);
        text.setFont(new Font("Arial", 13));
        VBox textBox = new VBox(text);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setMinWidth(60);

        TextField textField = new TextField();

        box.getChildren().addAll(textBox, textField);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 10, 0, 10));
        //infoNameList.add(text);
        //infoFieldList.add(textField);

        return box;
    }

    private VBox initListeCoursBox() {
        VBox listeCoursBox = new VBox();
        Label listeCoursLabel = new Label("Liste des cours");
        listeCoursLabel.setFont(new Font("Arial", 25));

        listView.getItems().addAll();

        sessionList.getItems().addAll("Automne", "Hiver", "Ete");
        sessionList.setValue("Automne");

        HBox boutonsBox = new HBox();
        boutonsBox.getChildren().addAll(sessionList, chargerButton);
        boutonsBox.setAlignment(Pos.CENTER);
        boutonsBox.setSpacing(10);


        listeCoursBox.getChildren().addAll(listeCoursLabel, listView, boutonsBox);
        listeCoursBox.setAlignment(Pos.CENTER);
        listeCoursBox.setPadding(new Insets(0, 25, 0, 25));
        listeCoursBox.setSpacing(10);

        return listeCoursBox;
    }

    public Button getChargerButton() {
        return this.chargerButton;
    }

    public Button getEnvoyerButton() {
        return envoyerButton;
    }

    public ListView<String> getListView() {
        return this.listView;
    }

    public String getSession() {
        return this.sessionList.getSelectionModel().getSelectedItem();
    }

    public void updateListView(ArrayList<Course> courses) {
        this.listView.getItems().clear();
        for (Course course : courses) {
            this.listView.getItems().add(course.getCode() + " " + course.getName());
        }
    }

    public int getSelectedCours() {
        return this.listView.getSelectionModel().getSelectedIndex();
    }

/*
    public String getTextInfo(String info) {
        for (int i = 0; i < infoNameList.size(); i++) {
            if (infoNameList.get(i).getText().equals(info)) {
                return infoFieldList.get(i).getText();
            }
        }
        return null;
    }

 */


    public String getTextInfo(String info) {
        for (Node row : infoBox.getChildren()) {
            VBox nameBox = (VBox) ((HBox)row).getChildren().get(0);
            Text nameText = (Text) nameBox.getChildren().get(0);
            TextField textField = (TextField) ((HBox)row).getChildren().get(1);
            if (nameText.getText().equals(info)) {
                return textField.getText();
            }
        }
        return null;
        // TODO: exception mauvaise adresse email
    }



}
