package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import webserver.WebServer;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServerGUI extends Application {

    public static int SERVER_LISTENING_PORT = 10008;
    public static String SERVER_ADDRESS = "http://localhost:";
    public static String SERVER_ROOT_WEB = "..\\svv-project\\src\\main\\java\\html\\";
    public static String SERVER_MAINTENANCE = "..\\svv-project\\src\\main\\java\\html\\maintenance\\index.html";

    public static ServerSocket serverSocketGUI = null;
    public static WebServer webServerGUI = null;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WebServerGUI.class.getResource("WebServer.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load(), 1240, 1024);
        stage.setTitle("SVV Web Server");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        server();
        launch();
    }

    private static void server() {
        if(webServerGUI != null) {
            webServerGUI.SERVER_STATUS = "STOP_SERVER";
            return;
        }

        webServerGUI.SERVER_STATUS = "RUN_SERVER";
        Thread startServer= new Thread(WebServer::initializeServer);
        startServer.start();

         Thread server = new Thread(() -> {
            try {
                while (true) {
                    if(!webServerGUI.SERVER_STATUS.equals("STOP_SERVER")) {
                        serverSocketGUI = new ServerSocket(SERVER_LISTENING_PORT);
                        try {
                            while (!webServerGUI.SERVER_STATUS.equals("STOP_SERVER")) {
                                System.out.println("Waiting for Connection");
                                new WebServer(serverSocketGUI.accept(), SERVER_ROOT_WEB, SERVER_MAINTENANCE);
                            }
                            System.out.println(SERVER_LISTENING_PORT);
                        } catch (IOException e) {
                            System.err.println("Accept failed.");
                        }
                        System.out.println("After while");
                    }
                    System.out.println(SERVER_LISTENING_PORT + " " + webServerGUI.SERVER_STATUS);
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port: 10008.");
            } finally {
                try {
                    serverSocketGUI.close();
                } catch (IOException e) {
                    System.err.println("Could not close port: 10008.");
                }
            }
        });

         server.start();
    }
}
