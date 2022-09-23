package backend.http;

import backend.ApplicationModels.PopulatedInstitution;
import backend.DatabaseModels.Institution;
import backend.Locator;
import backend.ApplicationModels.PopulatedInstitution;
import backend.controllers.InstitutionsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;
import java.util.LinkedList;
import java.util.List;

public class InstitutionsHttpHandler extends BaseHttpHandler {
    final InstitutionsController institutionsController = (InstitutionsController) Locator.instance
            .get(InstitutionsController.class);

    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody)
            throws Exception {
        if (pathValues[1].equals("institutions")) {
            final List<PopulatedInstitution> institutions = new LinkedList<>();
            institutions.addAll(institutionsController.listInstitutions());
            return new Gson().toJson(institutions);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in InstitutionsHttpHandler", -1);
        }
    }
}
