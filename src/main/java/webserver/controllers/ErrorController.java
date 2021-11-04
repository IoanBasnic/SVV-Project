package webserver.controllers;

import java.io.PrintStream;

public class ErrorController {
    public static String ErrorHeader(PrintStream os, String errMessage)
    {
        os.print("HTTP:/1.0 404 Not Found\n");
        os.print("Content-type: text/html\n");
        os.print("Content-length: "+errMessage.length()+"\n");
        os.print("\n");
        os.print(errMessage+"\n");

        return "Message sent to:" + os + "With the following message" + errMessage;
    }
}
