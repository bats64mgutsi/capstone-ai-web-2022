package backend.http;

import backend.ApplicationModels.PopulatedInstitution;
import backend.ApplicationModels.Stats;
import backend.DatabaseModels.Institution;
import backend.Locator;
import backend.ApplicationModels.PopulatedInstitution;
import backend.controllers.InstitutionsController;
import backend.controllers.StatsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;
import java.util.LinkedList;
import java.util.List;
public class StatsHttpHandler extends BaseHttpHandler {
    final StatsController statsController = (StatsController) Locator.instance
            .get(StatsController.class);

    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody)
            throws Exception {
        if (pathValues[1].equals("stats")) {
            return new Gson().toJson(statsController.computeStats());
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in StatsHttpHandler", -1);
        }
    }
}
