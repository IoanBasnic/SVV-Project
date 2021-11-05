package webserver.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.PrintStream;

public class ObjectFile {


    public static String FileFoundHeader(PrintStream os, int fileLength, File file)
    {
        String contentType = CheckFile(file.toString());
        os.print("HTTP:/1.0 200 OK\n");
        os.print("Content-type:" +  contentType + "\n");
        os.print("Content-length: "+fileLength+"\n");
        os.print("\n");

        if(contentType == null) return "Error when checking the file";
        return "Message sent to:" + os + " the file" + file + " content-type: " + contentType + " with file length:" + fileLength;
    }

    public static File OpenFile(String filename)
    {
        File file = new File(filename);
        if (file.exists()) return file;
        if (filename.charAt(0) != '/') return file;
        return new File(filename.substring(1));
    }

    public static String SendReply(PrintStream os, DataInputStream in, int flen)
    {
        try
        {
            byte[] buffer = new byte[flen];
            in.read(buffer);
            os.write(buffer, 0, flen);
            in.close();
        }
        catch (Exception e)  {
            System.out.println(e);
            return "Got an error when sending a reply to " + os;
        }
        return "Successfully sending the reply " + os;
    }

    private static String CheckFile(String fileExtension) {
        if(fileExtension.contains(".css")) return "text/css";
        if(fileExtension.contains(".html")) return "text/html";
        if(fileExtension.contains(".jpg")) return "image/jpg";
        return null;
    }
}
