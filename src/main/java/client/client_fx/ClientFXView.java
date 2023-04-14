package client.client_fx;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import server.models.Course;
import java.util.ArrayList;
import javafx.beans.binding.*;
import javafx.scene.control.Alert.AlertType;

/**
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
    private VBox infoBox = new VBox(10);


    /**
     * Constructeur de la view du ClientFX qui crée la scène javaFX.
     */
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

    /**
     * Construction de la partie inscription de l'interface avec chaque label/textField nécéssaire à l'inscription
     * d'un client.
     * @return Vbox avec tout les éléments relatifs à l'inscription.
     */
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

    /**
     * Construit une combinaison label/textField pour l'info renseigné.
     * @param info le contenu à afficher pour le label du textField.
     * @return hBox contenant label et textField.
     */
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


        return box;
    }

    /**
     * Construction de la partie affichage des cours qui affiche tous les cours disponible en fonction de la session
     * que l'on peut selectionner dans une liste déroulante.
     * @return la Vbox avec tout les éléments relatifs au chargement des cours.
     */
    private VBox initListeCoursBox() {
        VBox listeCoursBox = new VBox();
        Label listeCoursLabel = new Label("Liste des cours");
        listeCoursLabel.setFont(new Font("Arial", 25));

        Label emptyLabel = new Label("Aucune donnée");
        listView.setPlaceholder(emptyLabel);

        BooleanBinding empty = Bindings.isEmpty(listView.getItems());
        emptyLabel.visibleProperty().bind(empty);
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

    /**
     * Retourne le bouton de chargement des cours de la section d'affichage des cours.
     * @return bouton de chargement des cours
     */
    public Button getChargerButton() {
        return this.chargerButton;
    }

    /**
     * Retourne le bouton permettant la sousmission du formulaire d'inscription.
     * @return bouton d'envoi du formulaire d'inscription
     */
    public Button getEnvoyerButton() {
        return envoyerButton;
    }

    /**
     * Retourne la listView affichant les cours pour la section selectionnée.
     * @return listView des cours pour la session selectionnée.
     */
    public ListView<String> getListView() {
        return this.listView;
    }

    /**
     * Retourne la valeur sélectionnée de la liste déroulante des sessionsq.
     * @return la selection selectionnée
     */
    public String getSession() {
        return this.sessionList.getSelectionModel().getSelectedItem();
    }

    /**
     * Met à jours le contenu de la listView qui contient les cours disponible pour une certaine session.
     * @param courses ArrayList contenant les cours à afficher stockés en classe Course.
     */
    public void updateListView(ArrayList<Course> courses) {
        this.listView.getItems().clear();
        for (Course course : courses) {
            this.listView.getItems().add(course.getCode() + " " + course.getName());
        }
        this.listView.getSelectionModel().selectFirst();
    }

    /**
     * Retourne l'index de l'élément selectionné dans la listView.
     * @return index selectionné dans la listView.
     * @throws NullPointerException si aucun cours n'est selectionné.
     */
    public int getSelectedCours() throws NullPointerException {
        int index = this.listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            return index;
        }
        throw new NullPointerException("cours");
    }

    /**
     * Trouve la rangée associée à l'information recherchée du nom info.
     * Puis retourne le String présent dans le champ textuel à remplir y étant associé.
     * @param info le String à recher
     * @return le String contenu dans le champ à remplir associé à la catégorie info
     * @throws NullPointerException si le String dans le champ à remplir est vide
     * @throws NullPointerException OU s'il n'existe pas le champ info
     */
    public String getTextInfo(String info) throws NullPointerException {
        for (Node row : infoBox.getChildren()) {
            // prend le nom associé au champ à remplir
            VBox nameBox = (VBox) ((HBox)row).getChildren().get(0);
            Text nameText = (Text) nameBox.getChildren().get(0);

            // prend le String présent dans le champ
            TextField textField = (TextField) ((HBox)row).getChildren().get(1);

            // trouve s'il s'agit du nom que l'on recherche
            if (nameText.getText().equals(info)) {
                if (textField.getText().equals("")) {
                    throw new NullPointerException(nameText.getText());
                }
                return textField.getText();
            }
        }
        throw new NullPointerException("Champ non existant");
    }

    /**
     * Affiche une alerte de type erreur à l'utilisateur.
     * @param msg le message d'erreur à afficher.
     */
    public void alertErreur(String msg){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur...");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Affiche une alerte de type succès à l'utilisateur.
     * @param msg le message de succès à afficher.
     */
    public  void alertReussite(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Félicitation !");
        alert.setContentText(msg);

        alert.showAndWait();
    }

}
