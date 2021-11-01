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
import static org.mockito.Mockito.mock;

public class ErrorControllerTest {

    private ErrorController errorController;
    private ErrorController errorControllerMock = mock(ErrorController.class);
    @Before
    public void setUp() throws Exception {
        errorController = new ErrorController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void errorHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10011);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10011/");
        Mockito.doNothing().when(errorControllerMock).errorHeader(os, "ERR TEST MSG");
    }
}