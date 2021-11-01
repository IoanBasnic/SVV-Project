package webserver.controllers;

public class PathController {
    public static String getPath(String msg)
    {
        if (msg.length() == 0 || !msg.substring(0, 3).equals("GET")) return null;
        String path = msg.substring(msg.indexOf(' ')+1);
        path = path.substring(0, path.indexOf(' '));

        if(path.contains(".txt")) return getPathTxt(path);
        if(path.contains(".jpg")) return getPathJpg(path);
        if(path.contains(".css")) return getPathCss(path);

        if(path.contains(" ")) path = path.replace(" ", "%20");
        if (path.equals("")) return "..\\svv-project\\src\\main\\java\\html\\index\\index.html";
        if (path.charAt(path.length()-1) == '/') return  "..\\svv-project\\src\\main\\java\\html\\index\\index.html";
        return  "..\\svv-project\\src\\main\\java\\html\\TestServer\\" + path;
    }

    private static String getPathCss(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return "..\\svv-project\\src\\main\\java\\html\\" + path;
    }

    private static String getPathTxt(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return "..\\svv-project\\src\\main\\java\\html\\TestServer\\" + path;
    }

    private static String getPathJpg(String path)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
         return "..\\svv-project\\src\\main\\java\\html\\TestServer\\" + path;
    }

}
