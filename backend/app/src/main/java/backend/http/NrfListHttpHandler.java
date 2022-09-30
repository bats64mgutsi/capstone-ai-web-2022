package backend.http;

import backend.ApplicationModels.NrfAuthor;
import backend.ApplicationModels.Status;
import backend.Locator;
import backend.controllers.AuthorizationController;
import backend.controllers.NrfListController;
import backend.services.EmailService;
import backend.utils.BasicAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;

import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.sql.SQLException;
import java.util.LinkedList;

public class NrfListHttpHandler extends BaseHttpHandler {
    final NrfListController nrfListController = (NrfListController) Locator.instance.get(NrfListController.class);
    final AuthorizationController authorizationController = (AuthorizationController) Locator.instance
            .get(AuthorizationController.class);

    final EmailService emailService = (EmailService) Locator.instance.get(EmailService.class);

    Thread asyncSetAuthors = null;

    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody) throws Exception {
        if (getUpdateStatus()) {
            throw new RuntimeException("Author update is still in progress.");
        }

        if (pathValues[1].equals("nrfList")) {
            final String basicAuthString = requestHeaders.getFirst("Authorization");
            final String[] credentials = BasicAuth.getUsernameAndPassword(basicAuthString);
            final boolean isAdmin = authorizationController.isAdmin(credentials[0], credentials[1]);

            System.out.println("Poguss");
            if (!isAdmin) {
                return "AuthenticationError: Bad credentials given.";
            }

            Type listType = new TypeToken<LinkedList<NrfAuthor>>() {
            }.getType();
            LinkedList<NrfAuthor> nrfAuthors = new Gson().fromJson(requestBody, listType);
            System.out.println("Poguss");

            asyncSetAuthors = new Thread(() -> {
                try {
                    String year = requestHeaders.getFirst("Year");
                    nrfListController.setAuthors(nrfAuthors, year);
                    emailService.emailSystemDataUpdated();
                } catch (Exception e) {
                    try {
                        emailService.emailSystemDataUpdateError(e);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            asyncSetAuthors.start();

            return new Gson().toJson(nrfAuthors);
        } else if (pathValues[1].equals("status")) {
            return new Gson().toJson(check());
        }

        else {
            throw new InvalidPathException(pathValues[1], "No such path in NrfListHttpHandler", -1);
        }
    }

    private Status check() {//checks to see if the list is still updating
        boolean flag = getUpdateStatus();
        Status status = new Status(flag);
        return status;
    }

    private boolean getUpdateStatus() {   
        return asyncSetAuthors != null && asyncSetAuthors.isAlive();
    }
}
