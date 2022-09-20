package backend.ApplicationModels;

import java.util.List;
import java.util.Objects;

public class GoogleScholarAuthorProfile {
    public final List<String> subFields;
    public final List<GoogleScholarPublication> publications;

    public GoogleScholarAuthorProfile(List<String> subFields, List<GoogleScholarPublication> publications) {
        this.subFields = subFields;
        this.publications = publications;
    }

    @Override
    public String toString() {
        return "GoogleScholarAuthorProfile{" +
                "subFields=" + subFields +
                ", publications=" + publications +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleScholarAuthorProfile that = (GoogleScholarAuthorProfile) o;
        return Objects.equals(subFields, that.subFields) && Objects.equals(publications, that.publications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subFields, publications);
    }
}
