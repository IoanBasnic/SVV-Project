package svv;

import webserver.WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10008);
            System.out.println("Connection Socket Created");

            try {
                while(true) {
                    System.out.println("Waiting for Connection");
                    new Main(serverSocket.accept());
                }
            } catch (IOException var12) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException var13) {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException var11) {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }

        }

    }

    private Main(Socket clientSoc) {
        this.clientSocket = clientSoc;
        this.start();
    }

    public void run() {
        System.out.println("New Communication Thread Started");

        try {
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            String inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                out.println(inputLine);
                if (inputLine.trim().equals("")) {
                    break;
                }
            }

            out.close();
            in.close();
            this.clientSocket.close();
        } catch (IOException var4) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }

    }
}
