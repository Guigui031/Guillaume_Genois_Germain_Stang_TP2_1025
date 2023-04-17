package client.client_fx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 * Notez que cette classe est completement independante de toute definition
 * de comportement.
 */
public class ClientFXView extends BorderPane {

    private Button inc = new Button("+1");
    private Button dub = new Button("*2");
    private Button div = new Button("/2");
    private Button dec = new Button("-1");

    private Text textValeur = new Text("Appuyer sur un bouton");

    public ClientFXView() {

        this.setTop(inc);
        this.setBottom(dec);
        this.setLeft(dub);
        this.setRight(div);

        this.setCenter(textValeur);

        BorderPane.setAlignment(inc, Pos.CENTER);
        BorderPane.setAlignment(dub, Pos.CENTER);
        BorderPane.setAlignment(div, Pos.CENTER);
        BorderPane.setAlignment(dec, Pos.CENTER);

    }

    public Button getIncButton() {
        return this.inc;
    }

    public Button getDecButton() {
        return this.dec;
    }

    public Button getDivButton() {
        return this.div;
    }

    public Button getDubButton() {
        return this.dub;
    }

    public void updateText(String nouvelleValeur) {
        this.textValeur.setText(String.valueOf(nouvelleValeur));
    }

}
