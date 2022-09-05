package backend.ApplicationModels;
import java.util.*;

import backend.DatabaseModels.Publication;

public class GoogleScholarPublications {
    Publication publication;
    public List<String> coAuthors;

    public GoogleScholarPublications(Publication publication, List<String> coAuthors)
    {
        this.publication=publication;
        this.coAuthors=coAuthors;
    }
}
