package webserver;

import javax.xml.ws.spi.http.HttpExchange;
import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class WebServer extends Thread {
	protected Socket clientSocket;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10008);
			System.out.println("Connection Socket Created");
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
		start();
	}

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
//			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
//					true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					clientSocket.getInputStream()));
//
//			String inputLine;
//
//			while ((inputLine = in.readLine()) != null) {
//				System.out.println("Server: " + inputLine);
//				out.println(inputLine);
//
//				if (inputLine.trim().equals(""))
//					break;
//			}
//
//			out.close();
//			in.close();
			//clientSocket.close();

			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String path;
			if ((path = getPath(is.readLine())) != null) {
				File file = OpenFile(path);
				if (file.exists()) {
					try {
						in = new DataInputStream(new FileInputStream(file));
						fileFoundHeader(os, (int) file.length());
						sendReply(os, in, (int) file.length());
					} catch (Exception e) {
						errorHeader(os, "< h2 >Can't Read " + path + "< /h2 >");
					}
					os.flush();
				} else
					errorHeader(os, "< h2 >Not Found " + path + "< /h2 >");
			}
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	static String getPath(String msg)
	{
		if (msg.length() == 0 || msg.substring(0,3) == "GET") return null;
		String path = msg.substring(msg.indexOf(' ')+1);
		path = path.substring(0, path.indexOf(' '));
		if (path.equals("")) return "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
		if (path.charAt(path.length()-1) == '/') path += "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
		return path;
	}

	static void fileFoundHeader(PrintStream os, int filelength)
	{
		os.print("HTTP:/1.0 200 OK\n");
		os.print("Content-type: text/html\n");
		os.print("Content-length: "+filelength+"\n");
		os.print("\n");
	}

	static void sendReply(PrintStream os, DataInputStream in, int flen)
	{
		try
		{
			byte buffer[] = new byte[flen];
			in.read(buffer);
			os.write(buffer, 0, flen);
			in.close();
		}
		catch (Exception e)  { System.out.println(e); }
	}

	static void errorHeader(PrintStream os, String errmessage)
	{
		os.print("HTTP:/1.0 404 Not Found\n");
		os.print("Content-type: text/html\n");
		os.print("Content-length: "+errmessage.length()+"\n");
		os.print("\n");
		os.print(errmessage+"\n");
	}

	static File OpenFile(String filename)
	{
		File file = new File(filename);
		if (file.exists()) return file;
		if (filename.charAt(0) != '/') return file;
		return new File(filename.substring(1));
	}
}