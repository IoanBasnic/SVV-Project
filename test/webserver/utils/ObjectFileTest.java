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
    private int fileLength;
    @Before
    public void setUp() {
        objectFile = new ObjectFile();
        fileLength = 30;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void fileFoundHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10007);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10007/");

        File file = new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html");
        String expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/html" + " with file length:" + fileLength;
        assertEquals("Expected output to succeed when checking the file", expectedOutput, objectFile.FileFoundHeader(os, fileLength, file));

        file = new File("..\\svv-project\\src\\main\\java\\html\\index\\styles.css");
        expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/css" + " with file length:" + fileLength;
        assertEquals("Expected output to succeed when checking the file", expectedOutput, objectFile.FileFoundHeader(os, fileLength, file));

        file = new File("..\\svv-project\\src\\main\\java\\html\\index\\TestServer\\yes.jpg");
        expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "image/jpg" + " with file length:" + fileLength;
        assertEquals("Expected output to succeed when checking the file", expectedOutput, objectFile.FileFoundHeader(os, fileLength, file));

        file = new File("..\\svv-project\\src\\main\\java\\html\\index\\TestServer\\yes.js");
        expectedOutput = "Error when checking the file";
        assertEquals("Expected output to fail when checking the file", expectedOutput, objectFile.FileFoundHeader(os, fileLength, file));
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

        File file = new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html");
        String expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/html" + " with file length:" + fileLength;
        assertEquals("Expected output: ", expectedOutput, objectFile.FileFoundHeader(os, fileLength, file));

        DataInputStream in = new DataInputStream(new FileInputStream(new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html")));
        assertEquals("Expected output to succeed ", "Successfully sending the reply " + os, objectFile.SendReply(os, in, (int) new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html").length()));

        assertEquals("Expected output to fail ", "Got an error when sending a reply to " + os, objectFile.SendReply(os, in, -30));
    }
}