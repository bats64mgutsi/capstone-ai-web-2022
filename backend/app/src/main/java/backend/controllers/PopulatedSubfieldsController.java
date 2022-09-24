package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedInstitution;
import backend.ApplicationModels.PopulatedSubfield;
import backend.Tables.*;
import backend.services.*;
import java.sql.SQLException;
import java.util.*;

public class PopulatedSubfieldsController {
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final AiKeywordsTable aiKeywordsTable = (AiKeywordsTable) Locator.instance.get(AiKeywordsTable.class);
    final HashingService hashingService = (HashingService) Locator.instance.get(HashingService.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance
            .get(AuthorToSubfieldTable.class);

    public List<PopulatedSubfield> listSubfields() throws SQLException {
        List<String> aiKeywords = aiKeywordsTable.listAll();
        List<PopulatedSubfield> populatedSubfields = new ArrayList<>();
        for (String k : aiKeywords) {
            String id = hashingService.flatten(k);
            String name = k;
            Subfield subfield = new Subfield(id, name);
            List<AuthorToSubfield> authors = authorToSubfieldTable.getAuthorSubfieldsBySubfield(subfield.id);
            int numberOfAuthorsCurrentYear = authors.size();
            int numberOfAuthorsPrevYear = authors.size();
            PopulatedSubfield populatedSubfield = new PopulatedSubfield(subfield, numberOfAuthorsCurrentYear,
                    numberOfAuthorsPrevYear);
            populatedSubfields.add(populatedSubfield);
        }

        return populatedSubfields;
    }
}
