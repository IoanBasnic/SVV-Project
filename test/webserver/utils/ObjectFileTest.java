package webserver.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ObjectFileTest {

    private ObjectFile objectFile;
    private ObjectFile objectFileMock = mock(ObjectFile.class);
    @Before
    public void setUp() throws Exception {
        objectFile = new ObjectFile();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fileFoundHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10007);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10007/");
        Mockito.verify(objectFileMock, calls(1)).fileFoundHeader(os, 30, new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html"));
        objectFile.fileFoundHeader(os, 30, new File("..\\svv-project\\src\\main\\java\\html\\TestServer\\a.html"));
        objectFile.fileFoundHeader(os, 30, new File("..\\svv-project\\src\\main\\java\\html\\index\\index.js"));
    }

    @Test
    public void openFile() {
        assertEquals("Expected result a new file",objectFile.OpenFile("thisshouldnotexist"), new File("thisshouldnotexist"));
        assertEquals("Expected result a new file",objectFile.OpenFile("/thisshouldnotexist"), new File("thisshouldnotexist"));
        assertEquals("Expected result a new file",objectFile.OpenFile("X:/thisshouldnotexist"), new File("X:/thisshouldnotexist"));
    }

    @Test
    public void sendReply() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10009);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10009/");
        Mockito.verify(objectFileMock, calls(1)).fileFoundHeader(os, 30, new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html"));
        DataInputStream in = new DataInputStream(new FileInputStream(new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html")));;
        objectFile.SendReply(os, in, (int) new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html").length());
    }
}