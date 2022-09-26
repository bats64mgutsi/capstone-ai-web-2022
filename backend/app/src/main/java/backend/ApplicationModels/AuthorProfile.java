package backend.ApplicationModels;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.Institution;
import backend.DatabaseModels.Publication;

import java.util.*;

public class AuthorProfile {
    public final Author author;
    public final List<String> subFields;
    public final List<Publication> publications;
    public final int citationCount;
    public final Institution institution;

    public AuthorProfile(Author author, List<String> subFields, List<Publication> publications, int citationCount, Institution institution) {
        this.subFields = subFields;
        this.author = author;
        this.publications = publications;
        this.citationCount = citationCount;
        this.institution = institution;
    }
}
