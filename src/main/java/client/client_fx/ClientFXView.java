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

    private ListView<String> listView = new ListView<>();
    private Button chargerButton = new Button("charger");
    private ComboBox<String> sessionList = new ComboBox<>();

    private Button envoyerButton = new Button("envoyer");
    private ArrayList<Text> infoNameList = new ArrayList<>();
    private ArrayList<TextField> infoFieldList = new ArrayList<>();

    public ClientFXView() {
        BorderPane root = new BorderPane();

        // Partie de droite inscription
        VBox inscriptionBox = initInscBox();

        // Partie de gauche liste des cours
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

        VBox infoBox = new VBox();

        HBox prenomBox = generateInfo("prenom");
        HBox nomBox = generateInfo("nom");
        HBox emailBox = generateInfo("email");
        HBox matriculeBox = generateInfo("matricule");

        infoBox.getChildren().addAll(prenomBox, nomBox, emailBox, matriculeBox);
        infoBox.setAlignment(Pos.CENTER);

        inscriptionBox.getChildren().addAll(inscriptionLabel, infoBox, envoyerButton);
        inscriptionBox.setPadding(new Insets(0, 10, 0, 10));
        inscriptionBox.setAlignment(Pos.CENTER);

        return inscriptionBox;
    }

    private HBox generateInfo(String info) {
        HBox box = new HBox();
        TextField textField = new TextField();
        Text text = new Text(info);
        box.getChildren().addAll(textField, text);
        infoNameList.add(text);
        infoFieldList.add(textField);

        return box;
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

    public String getTextInfo(String info) {
        for (int i = 0; i < infoNameList.size(); i++) {
            if (infoNameList.get(i).getText().equals(info)) {
                return infoFieldList.get(i).getText();
            }
        }
        return null;
    }

}
