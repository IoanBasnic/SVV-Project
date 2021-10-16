package webserver.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.PrintStream;

public class ObjectFile {

    public static void fileFoundHeader(PrintStream os, int filelength)
    {
        os.print("HTTP:/1.0 200 OK\n");
        os.print("Content-type: text/html\n");
        os.print("Content-length: "+filelength+"\n");
        os.print("\n");
    }

    public static File OpenFile(String filename)
    {
        File file = new File(filename);
        if (file.exists()) return file;
        if (filename.charAt(0) != '/') return file;
        return new File(filename.substring(1));
    }

    public static void sendReply(PrintStream os, DataInputStream in, int flen)
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
}
