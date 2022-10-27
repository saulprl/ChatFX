package ChatFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {

    String client = "/FXMLViews/ClientView.fxml";
    String client2 = "/FXMLViews/ClientView2.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(client));
        primaryStage.setTitle("Cliente 1");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setX(545);
        primaryStage.setY(30);
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource(client2));
        Stage stage = new Stage();
        stage.setTitle("Cliente 2");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.setX(545);
        stage.setY(346);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
