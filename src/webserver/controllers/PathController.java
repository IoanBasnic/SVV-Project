package webserver.controllers;

public class PathController {
    public static String getPath(String msg)
    {
        if (msg.length() == 0 || msg.substring(0,3) == "GET") return null;
        String path = msg.substring(msg.indexOf(' ')+1);
        path = path.substring(0, path.indexOf(' '));

        if(path.contains(".txt")) return getPathTxt(path);
        if(path.contains(".jpg")) return getPathJpg(path);
        if(path.contains(".css")) return getPathCss(path);

        if (path.equals("")) return "..\\svv-project\\src\\html\\index\\index.html";
        if (path.equals("/a.html")) return  "..\\svv-project\\src\\html\\TestServer\\a.html";
        if (path.equals("/b.html")) return  "..\\svv-project\\src\\html\\TestServer\\b.html";
        if (path.equals("/a%20b.html")) return  "..\\svv-project\\src\\html\\TestServer\\a b.html";
        if (path.equals("a.html")) return  "..\\svv-project\\src\\html\\TestServer\\a.html";
        if (path.equals("b.html")) return  "..\\svv-project\\src\\html\\TestServer\\b.html";
        if (path.equals("a b.html")) return  "..\\svv-project\\src\\html\\TestServer\\a b.html";
        if (path.equals("aaa/b.html")) return  "..\\svv-project\\src\\html\\TestServer\\aaa\\b.html";
        if (path.equals("aaa/bbb/c.html")) return  "..\\svv-project\\src\\html\\TestServer\\aaa\\bbb\\c.html";
        if (path.charAt(path.length()-1) == '/') path += "..\\svv-project\\src\\html\\index\\index.html";
        else return "";
        return path;
    }

    private static String getPathCss(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return "..\\svv-project\\src\\html" + path;
    }

    private static String getPathTxt(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return "..\\svv-project\\src\\html\\TestServer\\" + path;
    }

    private static String getPathJpg(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
         return "..\\svv-project\\src\\html\\TestServer\\" + path;
    }

    public static String getPathMaintenance(String msg)
    {
        if (msg.length() == 0 || msg.substring(0,3) == "GET") return null;
        String path = msg.substring(msg.indexOf(' ')+1);
        path = path.substring(0, path.indexOf(' '));

        if (path.equals("")) return "..\\svv-project\\src\\html\\maintenance\\index.html";
        if (path.charAt(path.length()-1) == '/') path += "..\\svv-project\\src\\html\\maintenance\\index.html";
        else return "..\\svv-project\\src\\html\\maintenance\\index.html";
        if (path.equals("")) return "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
        if (path.charAt(path.length()-1) == '/') path += "C:\\Users\\Iani\\Desktop\\svv-project\\src\\html\\index.html";
    }
}
