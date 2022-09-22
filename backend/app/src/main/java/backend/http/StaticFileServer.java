package backend.http;

import com.google.common.io.Files;
import com.sun.net.httpserver.Headers;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class StaticFileServer extends BaseHttpHandler {
    private static final Map<String,String> MIME_MAP = new HashMap<>();
    static {
        MIME_MAP.put("appcache", "text/cache-manifest");
        MIME_MAP.put("css", "text/css");
        MIME_MAP.put("gif", "image/gif");
        MIME_MAP.put("html", "text/html");
        MIME_MAP.put("js", "application/javascript");
        MIME_MAP.put("json", "application/json");
        MIME_MAP.put("jpg", "image/jpeg");
        MIME_MAP.put("jpeg", "image/jpeg");
        MIME_MAP.put("mp4", "video/mp4");
        MIME_MAP.put("pdf", "application/pdf");
        MIME_MAP.put("png", "image/png");
        MIME_MAP.put("svg", "image/svg+xml");
        MIME_MAP.put("xlsm", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_MAP.put("xml", "application/xml");
        MIME_MAP.put("zip", "application/zip");
        MIME_MAP.put("md", "text/plain");
        MIME_MAP.put("txt", "text/plain");
        MIME_MAP.put("php", "text/plain");
    };

    @Override
    protected Object getResponse(String[] pathValues, Headers requestHeaders, String body, StringBuilder mimeType) throws Exception {
        final Path resourcesDir = Path.of("", "../client/dist/");
        final List<String> filteredPathValues = Arrays.stream(pathValues).filter(el -> !el.equals("static") && !el.isEmpty()).toList();

        String fileSubPath = String.join("/", filteredPathValues);
        if(filteredPathValues.isEmpty()) {
            fileSubPath = "index.html";
        }

        final String fileExtension = fileSubPath.substring(fileSubPath.lastIndexOf(".")+1);
        String mimeStr = MIME_MAP.get(fileExtension);
        if(mimeStr == null) mimeStr = "text/html";

        mimeType.replace(0, mimeType.length(), mimeStr);

        File file = resourcesDir.resolve(fileSubPath).toFile();
        if(file.exists()) {
            final String[] textualFileTypes = {"html", "txt", "js", "css", "svg"};
            if(Arrays.asList(textualFileTypes).contains(fileExtension)) {
                return IOUtils.toString(new FileReader(file));
            } else {
                return Files.toByteArray(file);
            }
        } else {
            // Return index page in case of single page app.
            return getResponse(new String[]{}, requestHeaders, body, mimeType);
        }
    }
}
