package backend.http;

import backend.ApplicationModels.NrfAuthor;
import backend.Locator;
import backend.controllers.AuthorizationController;
import backend.controllers.NrfListController;
import backend.utils.BasicAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;

import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.util.LinkedList;

public class NrfListHttpHandler extends BaseHttpHandler {
    final NrfListController nrfListController = (NrfListController) Locator.instance.get(NrfListController.class);
    final AuthorizationController authorizationController = (AuthorizationController) Locator.instance.get(AuthorizationController.class);

    @Override
    public String getResponseAsString(String[] pathValues, Headers requestHeaders, String requestBody) throws Exception {
        if(pathValues[1].equals("nrfList")) {
            final String basicAuthString = requestHeaders.getFirst("Authorization");
            final String[] credentials = BasicAuth.getUsernameAndPassword(basicAuthString);
            final boolean isAdmin = authorizationController.isAdmin(credentials[0], credentials[1]);

            if(!isAdmin) {
                return "AuthenticationError: Bad credentials given.";
            }

            Type listType = new TypeToken<LinkedList<NrfAuthor>>(){}.getType();
            LinkedList<NrfAuthor> nrfAuthors = new Gson().fromJson(requestBody, listType);

            nrfListController.setAuthors(nrfAuthors);

            return new Gson().toJson(nrfAuthors);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in NrfListHttpHandler", -1);
        }
    }
}
