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

public class AuthorsHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String outString = "";

        try {
            if("GET".equals(exchange.getRequestMethod())) {
                System.out.println("On new request to GET authors");
                final LinkedList<Author> authors = new LinkedList<>();
                authors.addAll(AuthorsController.listAuthors());
                outString = new Gson().toJson(authors);
            }
        } catch (SQLException e) {
            outString = e.toString();
        }

        OutputStream out = exchange.getResponseBody();
        exchange.getResponseHeaders().add("Content-type", "application/json");
        exchange.sendResponseHeaders(200, outString.length());
        out.write(outString.getBytes());
        out.flush();
        out.close();
    }
}
