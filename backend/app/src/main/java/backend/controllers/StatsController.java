package backend.controllers;

import backend.ApplicationModels.Stats;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedAuthor;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class StatsController {
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);

    final PopulatedSubfieldsController populatedSubfieldsController = (PopulatedSubfieldsController) Locator.instance.get(PopulatedSubfieldsController.class);

    public Stats computeStats() throws SQLException {
        final List<Author> currentYearAuthors = authorsTable.listAll();
        final List<Author> prevYearAuthors = authorsTable.listAllPrevYear();

        final List<Publication> allPublications = listPublicationsForAuthors(currentYearAuthors);
        allPublications.addAll(listPublicationsForAuthors(prevYearAuthors));

        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return new Stats(
            currentYearAuthors.size(),
            prevYearAuthors.size(),
            countPublicationsUpTo(allPublications, currentYear),
            countPublicationsUpTo(allPublications, currentYear-1),

            tallyCitationsUpTo(allPublications, currentYear),
            tallyCitationsUpTo(allPublications, currentYear-1),

            countAuthorsWithRating(currentYearAuthors, "A"),
            countAuthorsWithRating(currentYearAuthors, "B"),
            countAuthorsWithRating(currentYearAuthors, "C"),
            countAuthorsWithRating(currentYearAuthors, "P"),
            countAuthorsWithRating(currentYearAuthors, "Y"),

            countAuthorsWithRating(prevYearAuthors, "A"),
            countAuthorsWithRating(prevYearAuthors, "B"),
            countAuthorsWithRating(prevYearAuthors, "C"),
            countAuthorsWithRating(prevYearAuthors, "P"),
            countAuthorsWithRating(prevYearAuthors, "Y"),
            populatedSubfieldsController.listSubfields()
        );
    }

    private List<Publication> listPublicationsForAuthors(List<Author> authors) throws SQLException {
        final List<Contribution> contributions = new LinkedList<>();
        for(final Author author: authors) {
            contributions.addAll(contributionsTable.listForAuthor(author.id));
        }

        final List<Publication> publications = new LinkedList<>();
        for(final Contribution contribution: contributions) {
            publications.add(publicationsTable.getItemWithId(contribution.publicationId));
        }

        return publications;
    }

    private int countAuthorsWithRating(List<Author> authors, String withRating) {
        return authors.stream().filter(el -> el.rating.equals(withRating)).toList().size();
    }

    private int countPublicationsUpTo(List<Publication> publications, int upToYear) {
        return publications.stream().map(el -> {
            try{
                return Integer.parseInt(el.year);
            } catch(NumberFormatException e) {
                return 0;
            }
        }).filter(yearEl -> yearEl <= upToYear).toList().size();
    }

    private int tallyCitationsUpTo(List<Publication> publications, int upToYear) {
        return publications.stream().filter(el -> {
            try {
                int year = Integer.parseInt(el.year);
                return year <= upToYear;
            } catch (NumberFormatException e) {
                return true;
            }
        }).map(el -> el.citationCount).reduce(0, Integer::sum);
    }
}
