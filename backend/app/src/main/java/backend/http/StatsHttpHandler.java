package backend.http;

import backend.Locator;
import backend.controllers.StatsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;

public class StatsHttpHandler extends BaseHttpHandler {
    final StatsController statsController = (StatsController) Locator.instance
            .get(StatsController.class);

    @Override
    public String getResponse(String[] pathValues, Headers requestHeaders, String requestBody)
            throws Exception {
        if (pathValues[1].equals("stats")) {
            return new Gson().toJson(statsController.getStats());
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in StatsHttpHandler", -1);
        }
    }
}
