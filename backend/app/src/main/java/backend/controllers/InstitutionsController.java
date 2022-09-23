package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedInstitution;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class InstitutionsController {
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);

    public List<PopulatedInstitution> listInstitutions() throws SQLException {
        List<PopulatedInstitution> populatedInstitutions = new ArrayList<>();
        List<Institution> institutions = institutionsTable.listAll();
        List<String> institutionIds = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        for (Institution i : institutions) {        //puts all the institution ids into a list.
            String institutionId = i.id;
            institutionIds.add(institutionId);
        }
        for (int j=0 ; j<institutionIds.size();j++){   //loops through all the institutions and gets a list of authors for every intistution.
            int noAuthors = 0;
            int noPublications = 0;
            authors = authorsTable.getAuthors(institutionIds.get(j));
            noAuthors = authors.size();
            for (int k=0; k<noAuthors;k++){  //loops through all the authors per institution and gets the running total of all their combined publications.
                noPublications =noPublications+ contributionsTable.getNoPublications(authors.get(k).id);
            }
            PopulatedInstitution populatedInstitution = new PopulatedInstitution(institutionsTable.getInstitution(institutionIds.get(j)), noAuthors, noPublications);
            populatedInstitutions.add(populatedInstitution);
        }
        return populatedInstitutions;  // returns an object that has the number of authors and the number of combined publications attached to an institution.
    }
}
        
