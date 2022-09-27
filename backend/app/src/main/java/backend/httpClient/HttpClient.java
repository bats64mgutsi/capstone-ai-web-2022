package backend.httpClient;

import org.apache.commons.io.IOUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    private Map<String, Boolean> webScrapingKeyToExpiredStatus = new HashMap<>();

    public String lastOut = "";
    public String fetchWebPage(String path) {
        ensureWebScrapingKeysLoaded();
        final String proxyUrl = makeProxyUrl(path);

        try {
            final Document doc = Jsoup.connect(proxyUrl).get();
            String pageHtml = doc.html();
            if(pageHtml.contains("Please show you're not a robot")) {
                // Re-fetch web page with a different proxy server.
                return fetchWebPage(path);
            }

            return lastOut = pageHtml;
        }  catch (HttpStatusException e) {
            if(e.getStatusCode() == 429) {
                try {
                    Thread.sleep(100);
                    return lastOut = fetchWebPage(path);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if(e.getStatusCode() == 401) {
                for(final Map.Entry<String, Boolean> entry: webScrapingKeyToExpiredStatus.entrySet()) {
                    if(proxyUrl.contains(entry.getKey())) {
                        entry.setValue(true);
                    }
                }
                return fetchWebPage(path);
            }
        } catch(IOException e) {
            try {
                Thread.sleep(1000);
                return fetchWebPage(path);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        return "";
    }

    private String makeProxyUrl(String path) {
        final String urlEscapedPath = URLEncoder.encode(path, StandardCharsets.UTF_8);
        final String key = getNextKey();
        return "https://api.webscrapingapi.com/v1?api_key="+key+"&url="+urlEscapedPath;
    }

    private String getNextKey() {
        for(final Map.Entry<String, Boolean> key: webScrapingKeyToExpiredStatus.entrySet()) {
            if(!key.getValue()) {
                // Key is not expired.
                return key.getKey();
            }
        }

        throw new RuntimeException("All keys are expired.");
    }

    private void ensureWebScrapingKeysLoaded() {
        if(webScrapingKeyToExpiredStatus.isEmpty()) {
            String resourcesDirString = "src/main/data";

            try {
                if(!System.getProperty("user.dir").endsWith("/app")) {
                    // When run from the command line, the working directory is backend/app
                    // But when run from IntelliJ IDE, the working directory is backend/
                    resourcesDirString = "app/"+ resourcesDirString;
                }

                final Path resourcesDir = Path.of("", resourcesDirString);
                Path file = resourcesDir.resolve("web_scraping_keys.txt");
                final String webScrapingKeysAsString = IOUtils.toString(new FileReader(file.toFile()));
                final String[] webScrapingKeys = webScrapingKeysAsString.split("\n");

                for(final String key: webScrapingKeys) {
                    webScrapingKeyToExpiredStatus.put(key, false);
                }
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
