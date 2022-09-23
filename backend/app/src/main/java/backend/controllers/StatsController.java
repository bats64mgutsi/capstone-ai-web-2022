package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedAuthor;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class StatsController {
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);
   
    public int noOfAuthors() throws SQLException { //return all the authors in the database.
        
        int noAuthors =authorsTable.listAll().size();
        return noAuthors;
    }

    public int noOfPublications() throws SQLException {
        int noPublications = publicationsTable.noOfPublications();
        return noPublications;
    }

    public int noOfCitations() throws SQLException {
        List<Publication> publications = publicationsTable.listAll();
        int noCitations=0;
        for(Publication p : publications){
            noCitations = noCitations+p.citationCount;
        }
        return noCitations;
    }

    public int noOfRatedA() throws SQLException {
        List<Author> authors = authorsTable.listAll();
        int noRatedA=0;
        for (Author a : authors){
            if (a.rating.equals("A")){
                noRatedA++;
            }
        }
        return noRatedA;
    }

    public int noOfRatedB() throws SQLException {
        List<Author> authors = authorsTable.listAll();
        int noRatedB=0;
        for (Author a : authors){
            if (a.rating.equals("B")){
                noRatedB++;
            }
        }
        return noRatedB;
    }

    public int noOfRatedC() throws SQLException {
        List<Author> authors = authorsTable.listAll();
        int noRatedC=0;
        for (Author a : authors){
            if (a.rating.equals("C")){
                noRatedC++;
            }
        }
        return noRatedC;
    }

    public int noOfRatedP() throws SQLException {
        List<Author> authors = authorsTable.listAll();
        int noRatedP=0;
        for (Author a : authors){
            if (a.rating.equals("P")){
                noRatedP++;
            }
        }
        return noRatedP;
    }

    public int noOfRatedY() throws SQLException {
        List<Author> authors = authorsTable.listAll();
        int noRatedY=0;
        for (Author a : authors){
            if (a.rating.equals("Y")){
                noRatedY++;
            }
        }
        return noRatedY;
    }
}
