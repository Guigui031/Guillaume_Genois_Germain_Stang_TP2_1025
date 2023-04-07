package client.client_fx;
import client.ClientModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientFXLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ClientModel model = new ClientModel();
        ClientFXView view = new ClientFXView();
        ClientFXController controller = new ClientFXController(model, view);

        Scene scene = new Scene(view, 200, 200);

        stage.setScene(scene);
        stage.setTitle("Mon MVC JavaFXCompteur");
        System.out.println("lol");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
