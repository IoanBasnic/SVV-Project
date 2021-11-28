package svv;

import webserver.WebServer;

import java.io.IOException;
import java.net.ServerSocket;

import static webserver.WebServer.initializeServer;

public class Main {

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {

        WebServer webServer = null;
        Thread startServer=new Thread() {
            public void run() {
                initializeServer();
            }
        };
        startServer.start();

        try {
            serverSocket = new ServerSocket(10008);
            try {
                while (true) {
                    System.out.println("Waiting for Connection");
                    new WebServer(serverSocket.accept());
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 10008.");
            }
        }
    }
}
