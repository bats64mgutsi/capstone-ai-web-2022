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
            
            int noAuthorsCurrentYear =statsController.noOfAuthors();
            int noAuthorsPreviousYear =statsController.noOfAuthors();
            int noPublicationsCurrentYear =statsController.noOfPublications();
            int noPublicationsPreviousYear =statsController.noOfPublications();
            int noCitationsCurrentYear =statsController.noOfCitations();
            int noCitationsPreviousYear =statsController.noOfCitations();
            int ratedACurrentYear =statsController.noOfRatedA();
            int ratedBCurrentYear =statsController.noOfRatedB();
            int ratedCCurrentYear =statsController.noOfRatedC();
            int ratedPCurrentYear =statsController.noOfRatedP();
            int ratedYCurrentYear =statsController.noOfRatedY();
            int ratedAPreviousYear =statsController.noOfRatedA();
            int ratedBPreviousYear =statsController.noOfRatedB();
            int ratedCPreviousYear =statsController.noOfRatedC();
            int ratedPPreviousYear =statsController.noOfRatedP();
            int ratedYPreviousYear =statsController.noOfRatedY();
            Stats stats = new Stats(noAuthorsCurrentYear, noAuthorsPreviousYear, noPublicationsCurrentYear, noPublicationsPreviousYear, noCitationsCurrentYear, noCitationsPreviousYear, ratedACurrentYear, ratedBCurrentYear, ratedCCurrentYear, ratedPCurrentYear, ratedYCurrentYear, ratedAPreviousYear, ratedBPreviousYear, ratedCPreviousYear, ratedPPreviousYear, ratedYPreviousYear);
           // boolean trend =statsController.noOfAuthors();

            return new Gson().toJson(stats);
        } else {
            throw new InvalidPathException(pathValues[1], "No such path in StatsHttpHandler", -1);
        }
    }
}
