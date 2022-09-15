package backend.http;

import backend.utils.UrlParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String outString = "";
        try {
            String requestBody = IOUtils.toString(exchange.getRequestBody());
            outString = getResponseAsString(UrlParser.parsePaths(exchange.getRequestURI().getPath()), exchange.getRequestHeaders(), requestBody);
        } catch (Exception e) {
            outString = e.toString();
        }

        System.out.printf("On new request to %s %s\n", (CharSequence) exchange.getRequestMethod(), (CharSequence) exchange.getRequestURI().getPath());

        exchange.getResponseHeaders().add("Content-type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        exchange.getResponseHeaders().add("Access-Control-Max-Age", "86400");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.sendResponseHeaders(200, outString.length());

        OutputStream out = exchange.getResponseBody();
        out.write(outString.getBytes());
        out.flush();
        out.close();
    }

    protected abstract String getResponseAsString(String[] pathValues, Headers requestHeaders, String body);
}
