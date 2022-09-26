package backend.http;

import backend.ApplicationModels.PopulatedInstitution;
import backend.ApplicationModels.PopulatedSubfield;
import backend.DatabaseModels.Institution;
import backend.Locator;
import backend.controllers.InstitutionsController;
import backend.controllers.PopulatedSubfieldsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;
import java.util.LinkedList;
import java.util.List;

public class PopulatedSubfieldsHttpHandler extends BaseHttpHandler {
    final PopulatedSubfieldsController populatedSubfieldsController = (PopulatedSubfieldsController) Locator.instance
            .get(PopulatedSubfieldsController.class);

    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody)
            throws Exception {
        if (pathValues[1].equals("subfields")) {
            final List<PopulatedSubfield> populatedSubfields = new LinkedList<>();
            populatedSubfields.addAll(populatedSubfieldsController.listSubfields());
            return new Gson().toJson(populatedSubfields);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in PopulatedSubfieldsHttpHandler", -1);
        }
    }
}
