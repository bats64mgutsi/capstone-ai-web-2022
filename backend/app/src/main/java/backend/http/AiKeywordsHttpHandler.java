package backend.http;
import backend.Locator;
import backend.controllers.AiKeywordsController;

import backend.controllers.AuthorizationController;
import backend.utils.BasicAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;

import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AiKeywordsHttpHandler extends BaseHttpHandler {
    final AiKeywordsController aiKeywordsController = (AiKeywordsController) Locator.instance.get(AiKeywordsController.class);
    final AuthorizationController authorizationController = (AuthorizationController) Locator.instance.get(AuthorizationController.class);

    Thread asyncSetKeywords = null;


    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody) throws Exception {
        if(pathValues[1].equals("filters")) {
            final List<String> aiKeywords = new LinkedList<>(aiKeywordsController.listAiKeywords());
            return new Gson().toJson(aiKeywords);
        } else if(pathValues[1].equals("setFilters")) {
            if(asyncSetKeywords != null && asyncSetKeywords.isAlive()) {
                throw new RuntimeException("AI Keywords update is still in progress.");
            }

            final String basicAuthString = requestHeaders.getFirst("Authorization");
            final String[] credentials = BasicAuth.getUsernameAndPassword(basicAuthString);
            final boolean isAdmin = authorizationController.isAdmin(credentials[0], credentials[1]);

            if(!isAdmin) {
                return "AuthenticationError: Bad credentials given.";
            }

            Type listType = new TypeToken<LinkedList<String>>(){}.getType();
            LinkedList<String> aiKeywords = new Gson().fromJson(requestBody, listType);

            asyncSetKeywords = new Thread(() -> {
                try {
                    aiKeywordsController.setAiKeywords(aiKeywords);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });

            asyncSetKeywords.start();

            return new Gson().toJson(aiKeywords);
        }
         else {
            throw new InvalidPathException(pathValues[1], "No such path in AiKeywordsHttpHandler", -1);
        }
    }
    
}
