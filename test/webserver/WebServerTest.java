package webserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webserver.controllers.ErrorController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class WebServerTest {

    WebServer webServer = null;
    private ErrorController errorControllerMock =mock(ErrorController.class);;
    private PathController pathControllerMock = mock(PathController.class);;
    private ObjectFile objectFileMock = mock(ObjectFile.class);;
    @Before
    public void setUp() {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void WebServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10019);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);
    }

    @Test
    public void InitializeServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10013);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);
        webServer.initializeServer();
        webServer.SERVER_STATUS = "STOP_SERVER";
    }
    @Test
    public void TestMaintenanceServerMock() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10018);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);

        String path = "..\\svv-project\\src\\main\\java\\html\\maintenance\\index.html";
        File file = new File(path);
        assertEquals("Expected a good path for the file", file, objectFileMock.OpenFile(path));

        String errMessage = "ERROR MESSAGE TEST";
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        assertEquals("Expected an error output", "Message sent to:" + os + "With the following message" + errMessage, errorControllerMock.errorHeader(os, errMessage));

        String expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/html" + " with file length:" + (int) file.length();
        assertEquals("Expected output to succeed when checking the file", expectedOutput, objectFileMock.FileFoundHeader(os, (int) file.length(), file));

        webServer.maintenanceServer();
    }

    @Test
    public void TestRunMock() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10017);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);

        assertEquals("Expected a good path", "..\\svv-project\\src\\main\\java\\html\\index\\index.html", pathControllerMock.getPath("GET / HTTP/1.1"));

        String path = "..\\svv-project\\src\\main\\java\\html\\index\\index.html";
        File file = new File(path);
        assertEquals("Expected a good path for the file", file, objectFileMock.OpenFile(path));

        String errMessage = "ERROR MESSAGE TEST";
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        assertEquals("Expected an error output", "Message sent to:" + os + "With the following message" + errMessage, errorControllerMock.errorHeader(os, errMessage));

        String expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/html" + " with file length:" + (int) file.length();
        assertEquals("Expected output to succeed when checking the file", expectedOutput, objectFileMock.FileFoundHeader(os, (int) file.length(), file));

        webServer.run();
    }
}