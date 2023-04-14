package client.client_fx;
import client.ClientModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Dans cette classe nous définissons le launcher de clientfx.
 * Qui définit le controller et la view et leur intéraction.
 */
public class ClientFXLauncher extends Application {

    /**
     * Permet le démarage de la stage javaFx avec la scéne générée par la vue et controllée par le controller.
     * @param stage la stage javaFX.
     */
    @Override
    public void start(Stage stage) {
        ClientModel model = new ClientModel();
        ClientFXView view = new ClientFXView();
        ClientFXController controller = new ClientFXController(model, view);

        Scene scene = new Scene(view, 700, 500);


        stage.setScene(scene);
        stage.setTitle("Portail d'inscription de l'UDEM");
        stage.show();
    }

    /**
     * Permet le lancement de l'application javaFX avec les paramètres qui lui sont passés.
     * @param args paramètre le lancement de l'application javaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
