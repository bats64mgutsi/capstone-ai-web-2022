package backend.ApplicationModels;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.Publication;

import java.util.*;

public class AuthorProfile {
    public final Author author;
    public final List<String> subFields;
    public final List<Publication> publications;
    public final int citationCount;

    public AuthorProfile(Author author, List<String> subFields, List<Publication> publications, int citationCount) {
        this.subFields = subFields;
        this.author = author;
        this.publications = publications;
        this.citationCount = citationCount;
    }
}
