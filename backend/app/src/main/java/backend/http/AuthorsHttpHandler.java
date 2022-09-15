package backend.http;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.AuthorProfile;
import backend.controllers.AuthorsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AuthorsHttpHandler extends BaseHttpHandler {
    @Override
    public String getResponseAsString(String[] pathValues, Headers requestHeaders, String requestBody) {
        try {
            if(pathValues[1].equals("authors")) {
                final List<Author> authors = new LinkedList<>();
                authors.addAll(AuthorsController.listAuthors());
                return new Gson().toJson(authors);
            } else if (pathValues[1].equals("author")) {
                final String authorId = pathValues[2];
                final AuthorProfile authorProfile = AuthorsController.getProfile(authorId);
                return new Gson().toJson(authorProfile);
            }
        } catch (SQLException e) {
            return e.toString();
        }

        return "Unknown path in authors";
    }
}
