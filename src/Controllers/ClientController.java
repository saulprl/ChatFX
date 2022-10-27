package Controllers;

import AuxiliaryClasses.ConnectingMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientController {
    private static Socket socket;
    private static DataOutputStream outStream;
    private static DataInputStream inStream;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private CheckBox optionCheckBox;

    @FXML
    private Button sendButton;

    @FXML
    private Button connectButton;

    @FXML
    private void sendButtonPressed(ActionEvent event) {
        String message = messageTextField.getText();

        try {
            outStream.writeUTF(message);
            outStream.flush();
        } catch (IOException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido un error inesperado.");
            dialog.showAndWait();
        }
    }

    @FXML
    private void connectButtonPressed(ActionEvent event) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), Integer.parseInt(portTextField.getText()));
            outStream = new DataOutputStream(socket.getOutputStream());
            inStream = new DataInputStream(socket.getInputStream());
            String user = messageTextField.getText();

            String messageFormat = "<SERVER>: %s\n";

            textArea.appendText(String.format(messageFormat, inStream.readUTF()));
            outStream.writeUTF(user);
            textArea.appendText(String.format(messageFormat, inStream.readUTF()));
            outStream.writeUTF(optionCheckBox.getText());
            textArea.appendText(String.format(messageFormat, inStream.readUTF()));

            inStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido un error inesperado.");
            dialog.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        sendButton.setDisable(true);
        connectButton.setDisable(true);
        messageTextField.setPromptText("Ingresa un nombre de usuario.");

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}")) {
                portTextField.setText(oldValue);
            } else if (!newValue.isEmpty()) {
                connectButton.setDisable(false);
            }

            if (newValue.isEmpty()) {
                connectButton.setDisable(true);
            }
        });

        messageTextField.textProperty().addListener((observable) -> {
            if (messageTextField.getText().isEmpty()) {
                connectButton.setDisable(true);
            } else {
                connectButton.setDisable(false);
            }
        });

        optionCheckBox.selectedProperty().addListener((observable) -> {
            if (optionCheckBox.isSelected()) {
                optionCheckBox.setText("2");
            } else {
                optionCheckBox.setText("1");
            }
        });
    }
}
