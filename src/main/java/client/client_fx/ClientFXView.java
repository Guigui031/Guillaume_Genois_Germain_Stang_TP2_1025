package client.client_fx;
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
    private Button chargerButton = new Button("Charger");
    private ComboBox<String> sessionList = new ComboBox<>();

    public ClientFXView() {
        BorderPane root = new BorderPane();

        // Partie de droite inscription
        VBox inscriptionBox = new VBox();
        StackPane stackPane = new StackPane();
        Label inscriptionLabel = new Label("Inscription");
        stackPane.getChildren().add(inscriptionLabel);
        stackPane.setAlignment(Pos.CENTER);
        inscriptionBox.getChildren().add(stackPane);
        inscriptionBox.setPadding(new Insets(0, 10, 0, 10));
        inscriptionBox.setAlignment(Pos.CENTER);

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

    public Button getChargerButton() {
        return this.chargerButton;
    }

    public ListView getListViex() {
        return this.listView;
    }

    public ComboBox getSessionList() {
        return this.sessionList;
    }

    public void updateListView(ArrayList<String> list) {
        this.listView.getItems().clear();
        this.listView.getItems().addAll(list);
    }

    public String getSelectedCours() {
        return this.listView.getSelectionModel().getSelectedItem();
    }


}
