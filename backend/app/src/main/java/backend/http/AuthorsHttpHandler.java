package backend.http;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.AuthorProfile;
import backend.Locator;
import backend.ApplicationModels.PopulatedAuthor;
import backend.controllers.AuthorsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;
import java.util.LinkedList;
import java.util.List;

public class AuthorsHttpHandler extends BaseHttpHandler {
    final AuthorsController authorsController = (AuthorsController) Locator.instance.get(AuthorsController.class);

    @Override
    public String getResponseAsString(String[] pathValues, Headers requestHeaders, String requestBody) throws Exception {
        if(pathValues[1].equals("authors")) {
            final List<PopulatedAuthor> populatedAuthors = new LinkedList<>();
            populatedAuthors.addAll(authorsController.listAuthors());
            return new Gson().toJson(populatedAuthors);
        } else if (pathValues[1].equals("author")) {
            final String authorId = pathValues[2];
            final AuthorProfile authorProfile = authorsController.getProfile(authorId);
            return new Gson().toJson(authorProfile);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in AuthorsHttpHandler", -1);
        }
    }
}
