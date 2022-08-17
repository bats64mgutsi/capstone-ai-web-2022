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
    public String getResponseAsString(String[] pathValues) {
        try {
            if(pathValues[1].equals("authors")) {
                final List<Author> authors = new LinkedList<>();
                authors.addAll(AuthorsController.listAuthors());
                return new Gson().toJson(authors);
            } else if (pathValues[1].equals("author")) {
                final String authorId = pathValues[2];
                System.out.printf("Getting author %s\n", authorId);
                return "";
            }
        } catch (SQLException e) {
            return e.toString();
        }

        return "Unknown path in authors";
    }
}
