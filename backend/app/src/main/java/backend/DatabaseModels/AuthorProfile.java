package backend.DatabaseModels;

import java.util.*;

public class AuthorProfile {
    public final Author author;
    public final List<String> subFields;
    public final List<Publication> publications;

    public AuthorProfile(Author author, List<String> subFields, List<Publication> publications) {
        this.subFields = subFields;
        this.author = author;
        this.publications = publications;
    }
}
