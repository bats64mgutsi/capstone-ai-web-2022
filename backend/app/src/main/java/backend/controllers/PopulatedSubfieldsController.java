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
    final AiKeywordsTable aiKeywordsTable = (AiKeywordsTable) Locator.instance.get(AiKeywordsTable.class);
    final HashingService hashingService = (HashingService) Locator.instance.get(HashingService.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);

    public List<PopulatedSubfield> listSubfields() throws SQLException {
        List<String> aiKeywords = aiKeywordsTable.listAll();
        List<PopulatedSubfield> populatedSubfields = new ArrayList<>();
        for (String name : aiKeywords) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int previousYear = currentYear - 1;
            List<String> idUnits = new ArrayList<>();
            idUnits.add(name);
            idUnits.add(Integer.toString(currentYear));

            String id = hashingService.flatten(idUnits);
            List<String> idUnits2 = new ArrayList<>();
            idUnits2.add(name);
            idUnits2.add(Integer.toString(previousYear));

            String id2 = hashingService.flatten(idUnits2);

            Subfield subfield = new Subfield(hashingService.flatten(name), name);
            List<AuthorToSubfield> authors = authorToSubfieldTable.getAuthorSubfieldsBySubfield(id);
            List<AuthorToSubfield> authors2 = authorToSubfieldTable.getAuthorSubfieldsBySubfield(id2);

            int numberOfAuthorsCurrentYear = authors.size();
            int numberOfAuthorsPrevYear = authors2.size();
            PopulatedSubfield populatedSubfield = new PopulatedSubfield(subfield, numberOfAuthorsCurrentYear, numberOfAuthorsPrevYear);
            populatedSubfields.add(populatedSubfield);
        }

        // Return in descending order of numberOfAuthorsCurrentYear
        populatedSubfields.sort(Comparator.comparingInt(el -> -el.numberOfAuthorsCurrentYear));
        return populatedSubfields;
    }
}
