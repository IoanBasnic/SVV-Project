package webserver.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.mock;

public class ErrorControllerTest {

    private ErrorController errorController;
    @Before
    public void setUp() {
        errorController  = new ErrorController();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ErrorHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10011);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10011/");
        String errMessage = "ERR TEST MSG";
        assertEquals("Expected this output: ", "Message sent to:" + os + "With the following message" + errMessage,errorController.ErrorHeader(os, errMessage));
        assertNotNull(errorController);
    }
}