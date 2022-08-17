package backend.http;

import backend.controllers.AuthorsController;
import backend.models.Author;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String outString = getResponseAsString();

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

    protected abstract String getResponseAsString(List<String> pathValues);
}
