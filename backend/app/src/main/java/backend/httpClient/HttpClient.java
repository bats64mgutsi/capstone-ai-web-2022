package backend.httpClient;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    public String lastOut = "";
    public String fetchWebPage(String path) {
        try {
            final Document doc = Jsoup.connect(makeProxyUrl(path)).get();
            String pageHtml = doc.html();
            if(pageHtml.contains("Please show you're not a robot")){
                // Re-fetch web page with a different proxy server.
                pageHtml = fetchWebPage(path);
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
            }
        } catch(SocketTimeoutException e) {
            // Retry
            return fetchWebPage(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private String makeProxyUrl(String path) {
        final String urlEscapedPath = URLEncoder.encode(path, StandardCharsets.UTF_8);
        return "https://api.webscrapingapi.com/v1?api_key=uMcuS7qXrNYf0pRVp0WhSP6tdrYw4mB0&url="+urlEscapedPath;
    }
}
