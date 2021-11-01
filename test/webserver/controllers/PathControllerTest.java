package webserver.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathControllerTest {

    PathController pathController;
    @Before
    public void setUp() throws Exception {
        pathController = new PathController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPath() {
        assertEquals("Expecting index.html", "..\\svv-project\\src\\main\\java\\html\\index\\index.html", pathController.getPath("GET / HTTP/1.1"));
        assertEquals("Expecting null", null, pathController.getPath("POST / HTTP/1.1"));
        assertEquals("Expecting styles.css", "..\\svv-project\\src\\main\\java\\html\\/styles.css", pathController.getPath("GET /styles.css HTTP/1.1"));
        assertEquals("Expecting yes.jpg", "..\\svv-project\\src\\main\\java\\html\\TestServer\\/TestServer/yes.jpg", pathController.getPath("GET /TestServer/yes.jpg HTTP/1.1"));
        assertEquals("Expecting a.txt", "..\\svv-project\\src\\main\\java\\html\\TestServer\\/TestServer/a.txt", pathController.getPath("GET /TestServer/a.txt HTTP/1.1"));
        assertEquals("Expecting wrong string", "..\\svv-project\\src\\main\\java\\html\\TestServer\\/thisdoesnotexist", pathController.getPath("GET /thisdoesnotexist HTTP/1.1"));
    }
}
