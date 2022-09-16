package backend.utils;

public class UrlParser {
    public static String[] parsePaths(String paths) {
        if(paths.length() == 0) return new String[]{};
        return paths.split("/");
    }
}
