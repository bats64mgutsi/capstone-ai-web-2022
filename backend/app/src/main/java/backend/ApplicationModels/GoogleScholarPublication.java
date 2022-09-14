package backend.ApplicationModels;
import java.util.*;

import backend.DatabaseModels.Publication;

public class GoogleScholarPublication {
    Publication publication;
    public List<String> coAuthors;

    public GoogleScholarPublication(Publication publication, List<String> coAuthors)
    {
        this.publication=publication;
        this.coAuthors=coAuthors;
    }

    @Override
    public String toString() {
        return "GoogleScholarPublication{" +
                "publication=" + publication +
                ", coAuthors=" + coAuthors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleScholarPublication that = (GoogleScholarPublication) o;
        return Objects.equals(publication, that.publication) && Objects.equals(coAuthors, that.coAuthors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publication, coAuthors);
    }
}
