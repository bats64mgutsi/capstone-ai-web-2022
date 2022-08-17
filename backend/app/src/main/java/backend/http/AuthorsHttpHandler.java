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

public class AuthorsHttpHandler extends BaseHttpHandler {
    @Override
    public void getResponseAsString() {
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
    }
}
