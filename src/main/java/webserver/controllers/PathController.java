package webserver.controllers;

public class PathController {
    public static String getPath(String msg, String rootPath)
    {
        if (msg.length() == 0 || !msg.substring(0, 3).equals("GET")) return null;
        String path = msg.substring(msg.indexOf(' ')+1);
        path = path.substring(0, path.indexOf(' '));

        if(path.contains(".txt")) return getPathTxt(path, rootPath);
        if(path.contains(".jpg")) return getPathJpg(path, rootPath);
        if(path.contains(".css")) return getPathCss(path, rootPath);

        if(path.contains(" ")) path = path.replace(" ", "%20");
        if (path.equals("")) return rootPath + "index\\index.html";
        if (path.charAt(path.length()-1) == '/') return  rootPath + "index\\index.html";
        return  rootPath + "TestServer\\" + path;
    }

    private static String getPathCss(String path, String rootPath)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return rootPath + path;
    }

    private static String getPathTxt(String path, String rootPath)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
        return rootPath + "TestServer\\" + path;
    }

    private static String getPathJpg(String path, String rootPath)
    {
        if(path.contains(" ")) path = path.replace(" ", "%20");
         return rootPath + "TestServer\\" + path;
    }

}
