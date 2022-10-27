package AuxiliaryClasses;

import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class ConnectingMethods implements Runnable {
    public int port;
//    public static ServerSocket server;
//    public static Socket socket;
    public DataInputStream inputStream;
    public String messageIn;
    public TextArea textArea;

    public ConnectingMethods(DataInputStream inputStream, TextArea textArea) {
//        this.port = port;
//        ConnectingMethods.server = server;
//        ConnectingMethods.socket = socket;
        this.inputStream = inputStream;
        this.messageIn = "";
        this.textArea = textArea;
    }

    @Override
    public void run() {
        communicate(Thread.currentThread().getName());
    }

    private void communicate(String threadName) {
        try {
            while (!messageIn.equals("***CLOSE***")) {
                messageIn = inputStream.readUTF();
                textArea.appendText(String.format("<%s>: %s\n", threadName, messageIn));
            }
        } catch (IOException ioEx) {

        }
    }

    /*private void connect(String threadName) {
        try {
            switch (threadName) {
                case "thread1":
                    port = 1234;
                    server = new ServerSocket(port);
                    socket = server.accept();
                    break;
                case "thread2":
                    port = 1223;
                    server = new ServerSocket(port);
                    socket = server.accept();
                    break;
            }
        } catch (IOException ioEx) {
            System.out.println("IOException: " + ioEx.getMessage());
        }
    }*/

}
