package webserver.controllers;

public class PathController {
    public static String getPath(String msg)
    {
        if (msg.length() == 0 || msg.substring(0,3) == "GET") return null;
        String path = msg.substring(msg.indexOf(' ')+1);
        path = path.substring(0, path.indexOf(' '));
        if (path.equals("")) return "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
        if (path.charAt(path.length()-1) == '/') path += "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
        return path;
    }
}
