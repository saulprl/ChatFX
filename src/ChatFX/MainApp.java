package ChatFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    String server = "/FXMLViews/ServerView.fxml";
    String server2 = "/FXMLViews/ServerView2.fxml";
    String login = "/FXMLViews/Login.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(server));
        primaryStage.setTitle("Servidor 1");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setX(20);
        primaryStage.setY(30);
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource(server2));
        Stage stage = new Stage();
        stage.setTitle("Servidor 2");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.setX(20);
        stage.setY(346);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
