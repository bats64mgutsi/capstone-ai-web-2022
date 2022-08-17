package backend.models;

import java.util.*;

public class AuthorProfile {
    private final List<String> subFields;
    private final List<Publication> publications;

    AuthorProfile(List<String> subFields, List<Publication> publications) {
        this.subFields = subFields;
        this.publications = publications;
    }
}
