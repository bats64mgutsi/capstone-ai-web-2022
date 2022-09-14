package backend.httpClient;

import org.jsoup.Jsoup;

import java.io.IOException;

public class HttpClient {
    public String fetchWebPage(String path) {
        try {
            return Jsoup.connect("").get().html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
