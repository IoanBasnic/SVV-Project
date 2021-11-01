package webserver;

import webserver.controllers.ErrorController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.net.*;
import java.io.*;

import java.util.Scanner;

public class WebServer extends Thread {
    private static ServerSocket serverSocket = null;
    private Socket clientSocket;
	private static WebServer server = null;
	private ErrorController errorController = new ErrorController();
	private PathController pathController = new PathController();
	private ObjectFile objectFile = new ObjectFile();

	private static String SERVER_STATUS = "STOP_SERVER";
	public static void main(String[] args) throws IOException {

		Thread startServer=new Thread() {
			public void run() {
				InitializeServer();
			}
		};
		startServer.start();
		File testFile = new File("");
		String currentPath = testFile.getAbsolutePath();
		System.out.println("current path is: " + currentPath);
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

	WebServer(Socket clientSoc) throws IOException {
		clientSocket = clientSoc;
		if(SERVER_STATUS.equals("RUN_SERVER")) start();
		if(SERVER_STATUS.equals("MAINTENANCE_SERVER")) MaintenanceServer();
		if(SERVER_STATUS.equals("STOP_SERVER")) StopServer();
	}

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String path;
			if ((path = pathController.getPath(is.readLine())) != null) {
				File file = objectFile.OpenFile(path);
				if (file.exists()) {
					try {
						in = new DataInputStream(new FileInputStream(file));
						objectFile.fileFoundHeader(os, (int) file.length(), file);
						objectFile.SendReply(os, in, (int) file.length());
					} catch (Exception e) {
						errorController.errorHeader(os, "Can't Read " + path);
					}
					os.flush();
				} else
					errorController.errorHeader(os, "Not Found " + path);
			}
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	private static void InitializeServer() {

        try {
            if(SERVER_STATUS.equals("EXIT")) serverSocket.close();
        } catch (Exception e) {
          System.out.println(e);
        };
		System.out.println("Enter SERVER STATUS:\t0: STOP\t1: MAINTENANCE\t2: RUN\n");
		System.out.println("CURRENT SERVER STATUS: " + SERVER_STATUS);
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		if(myObj.nextLine().equals("0")) SERVER_STATUS = "STOP_SERVER";
		if(myObj.nextLine().equals("1")) SERVER_STATUS = "MAINTENANCE_SERVER";
		if(myObj.nextLine().equals("2")) SERVER_STATUS = "RUN_SERVER";
        if(myObj.nextLine().equals("9")) SERVER_STATUS = "EXIT";
		System.out.println("\nNEW CURRENT SERVER STATUS: " + SERVER_STATUS + "\n");  // Output user input

		InitializeServer();
	}


	private void MaintenanceServer() {
		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			File file = objectFile.OpenFile("..\\svv-project\\src\\main\\java\\html\\maintenance\\index.html");
			try {
				in = new DataInputStream(new FileInputStream(file));
				objectFile.fileFoundHeader(os, (int) file.length(), file);
				objectFile.SendReply(os, in, (int) file.length());
			} catch (Exception e) {
				errorController.errorHeader(os, "Can't read Maintenance html file");
			}
			os.flush();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	private void StopServer() {

	}
}