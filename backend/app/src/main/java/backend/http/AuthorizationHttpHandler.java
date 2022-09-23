package backend.http;

import backend.ApplicationModels.LoginCredentials;
import backend.Locator;
import backend.controllers.AuthorizationController;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;

public class AuthorizationHttpHandler extends BaseHttpHandler {
    final AuthorizationController authorizationController = (AuthorizationController) Locator.instance.get(AuthorizationController.class);

    @Override
    protected String getResponse(String[] pathValues, Headers requestHeaders, String body) throws Exception {
        if(pathValues[1].equals("validate")) {
            final LoginCredentials loginCredentials = new Gson().fromJson(body, LoginCredentials.class);
            final boolean isValid = authorizationController.isAdmin(loginCredentials.username, loginCredentials.password);
            if(!isValid) {
                throw new IllegalArgumentException("Bad credentials. Could not validate.");
            }

            return new Gson().toJson(loginCredentials);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in AuthorizationHttpHandler", -1);
        }
    }
}
