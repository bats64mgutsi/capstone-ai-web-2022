package backend.data;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ListAuthorsHttp implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("On new request to authors/list");
        String outString = "[]";

        OutputStream out = exchange.getResponseBody();
        exchange.getResponseHeaders().add("Content-type", "application/json");
        exchange.sendResponseHeaders(200, outString.length());
        out.write(outString.getBytes());
        out.flush();
        out.close();
    }
}
