package webserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class WebServerTest {

    WebServer webServer;
    WebServer webServerMock = mock(WebServer.class);
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() throws IOException {
        String[] args = null;
        webServer.main(args);
        try {
            ServerSocket serverSocket = new ServerSocket(10008);
            webServer.main(args);
        } catch (Exception e) {
            System.out.println("Got the exception I wanted");
        }
    }

    @Test
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10016);
        Socket clientSocket = serverSocket.accept();
        try {
            webServer.run();
            fail();
        } catch (Exception e) {
            System.out.println("Got the exception");
        }
    }

    @Test
    public void WebServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10013);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);
    }

}