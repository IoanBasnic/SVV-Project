package webserver;

import webserver.controllers.ErrorController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class WebServer extends Thread {
	protected Socket clientSocket;
	private static WebServer server = null;
	private ErrorController errorController = new ErrorController();
	private PathController pathController = new PathController();
	private ObjectFile objectFile = new ObjectFile();

	public static String SERVER_STATUS = "STOP_SERVER";
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		Thread startServer=new Thread() {
			public void run() {
				InitializeServer();
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
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10008.");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: 10008.");
				System.exit(1);
			}
		}
	}

	private WebServer(Socket clientSoc) {
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
						errorController.errorHeader(os, "< h2 >Can't Read " + path + "< /h2 >");
					}
					os.flush();
				} else
					errorController.errorHeader(os, "< h2 >Not Found " + path + "< /h2 >");
			}
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	public static void InitializeServer() {

		System.out.println("Enter SERVER STATUS:\t0: STOP\t1: MAINTENANCE\t2: RUN\n");
		System.out.println("CURRENT SERVER STATUS: " + SERVER_STATUS);
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		if(myObj.nextLine().equals("0")) SERVER_STATUS = "STOP_SERVER";
		if(myObj.nextLine().equals("1")) SERVER_STATUS = "MAINTENANCE_SERVER";
		if(myObj.nextLine().equals("2")) SERVER_STATUS = "RUN_SERVER";
		System.out.println("\nNEW CURRENT SERVER STATUS: " + SERVER_STATUS + "\n");  // Output user input

		InitializeServer();
	}


	public void MaintenanceServer() {
		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			File file = objectFile.OpenFile("..\\svv-project\\src\\html\\maintenance\\index.html");
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

	public void StopServer() {
//		try {
////			PrintStream os = new PrintStream(clientSocket.getOutputStream());
////			errorController.errorHeader(os, "404 ERR");
////			clientSocket.close();
//		} catch (IOException e) {
//			System.err.println("Problem with Communication Server");
//			System.exit(1);
//		}
	}
}