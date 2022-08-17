package backend.models;

import java.util.*;

public class AuthorProfile {
    private final Author author;
    private final List<String> subFields;
    private final List<Publication> publications;

    public AuthorProfile(Author author, List<String> subFields, List<Publication> publications) {
        this.subFields = subFields;
        this.author = author;
        this.publications = publications;
    }
}
