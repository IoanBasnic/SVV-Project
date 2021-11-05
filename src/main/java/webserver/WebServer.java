package webserver;

import webserver.controllers.ErrorController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.net.*;
import java.io.*;

import java.util.Scanner;

public class WebServer extends Thread {
    private Socket clientSocket;
	private ErrorController errorController = new ErrorController();
	private PathController pathController = new PathController();
	private ObjectFile objectFile = new ObjectFile();

	public static String SERVER_STATUS = "STOP_SERVER";

	public WebServer(Socket clientSoc) {
		clientSocket = clientSoc;
		if(SERVER_STATUS.equals("EXIT")) System.exit(1);
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
						objectFile.FileFoundHeader(os, (int) file.length(), file);
						objectFile.SendReply(os, in, (int) file.length());
					} catch (Exception e) {
						errorController.ErrorHeader(os, "Can't Read " + path);
					}
					os.flush();
				} else
					errorController.ErrorHeader(os, "Not Found " + path);
			}
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	public static void InitializeServer() {

		System.out.println("Enter SERVER STATUS:\t0: STOP\t1: MAINTENANCE\t2: RUN\t9: EXIT\n");
		System.out.println("CURRENT SERVER STATUS: " + SERVER_STATUS);
		Scanner myObj = new Scanner(System.in);
		if(myObj.nextLine().equals("0")) SERVER_STATUS = "STOP_SERVER";
		if(myObj.nextLine().equals("1")) SERVER_STATUS = "MAINTENANCE_SERVER";
		if(myObj.nextLine().equals("2")) SERVER_STATUS = "RUN_SERVER";
        if(myObj.nextLine().equals("9")) SERVER_STATUS = "EXIT";
		System.out.println("\nNEW CURRENT SERVER STATUS: " + SERVER_STATUS + "\n");

		if(!SERVER_STATUS.equals("EXIT")) InitializeServer();
	}


	public void MaintenanceServer() {
		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			File file = objectFile.OpenFile("..\\svv-project\\src\\main\\java\\html\\maintenance\\index.html");
			try {
				in = new DataInputStream(new FileInputStream(file));
				objectFile.FileFoundHeader(os, (int) file.length(), file);
				objectFile.SendReply(os, in, (int) file.length());
			} catch (Exception e) {
				errorController.ErrorHeader(os, "Can't read Maintenance html file");
			}
			os.flush();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	private void StopServer() {
        // do nothing
	}
}