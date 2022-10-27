package Controllers;

import AuxiliaryClasses.ConnectingMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private static ServerSocket server;
    private static Socket socket;
    private static DataOutputStream outStream;
    private static DataInputStream inStream;
    private static int port;
    public String messageIn;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button startButton;

    @FXML
    private Button sendButton;

    @FXML
    private void sendButtonPressed(ActionEvent event) {
        String messageOut;

        try {
            messageOut = messageTextField.getText();
            outStream.writeUTF(messageOut);
            outStream.flush();
        } catch (IOException ioEx) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido un error inesperado.");
            dialog.showAndWait();
        }
    }

    @FXML
    private void startButtonPressed(ActionEvent event) {
        try {
            server = new ServerSocket(Integer.parseInt(portTextField.getText()));
            socket = server.accept();
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());

            String messageFormat = "<%s>: %s\n";

            outStream.writeUTF("Escribe tu nombre: ");
            String user = inStream.readUTF().trim();
            textArea.appendText(String.format(messageFormat, user, user));

            outStream.writeUTF("Ingresa el número 1 o 2.");
            int option = Integer.parseInt(inStream.readUTF().trim());
            textArea.appendText(String.format(messageFormat, user, Integer.toString(option)));

            String result;

            switch (option) {
                case 1:
                    result = odd();
                    break;
                case 2:
                    result = even();
                    break;
                default:
                    result = "Opción no válida.";
            }

            result = result.replaceAll(", $", ".");

            outStream.writeUTF(result);

            outStream.close();
            inStream.close();
            socket.close();
            server.close();
        } catch (IOException ioEx) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido un error inesperado.");
            dialog.showAndWait();
        }

//        ConnectingMethods connection = new ConnectingMethods();
//        Thread thread1 = new Thread(connection);
//        thread1.setName("thread1");
//        thread1.run();
//
//
//
//        server = connection.server;
//        socket = connection.socket;
//
//        System.out.println("Server: " + server);
    }

    @FXML
    private void initialize() {
        socket = null;
        server = null;
        startButton.setDisable(true);

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}")) {
                portTextField.setText(oldValue);
            } else if (!newValue.isEmpty()) {
                startButton.setDisable(false);
            }

            if (newValue.isEmpty()) {
                startButton.setDisable(true);
            }
        });
    }

    private String odd() {
        StringBuilder string = new StringBuilder();

        for (int i = 1; i <= 100; i += 2) {
            string.append(i).append(", ");
        }

        return string.toString();
    }

    private String even() {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i <= 100; i += 2) {
            if (i == 0) continue;
            string.append(i).append(", ");
        }

        return string.toString();
    }
}
