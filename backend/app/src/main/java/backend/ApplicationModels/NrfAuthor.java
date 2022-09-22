package backend.ApplicationModels;

import java.util.*;

public class NrfAuthor {
    public String surname;
    public String id;
    public String initials;
    public String title;
    public String institution;
    public String rating;
    public List<String> primaryResearchFields;
    public List<String> secondaryResearchFields;
    public List<String> specialisations;

    public NrfAuthor(String id, String surname, String initials, String title, String institution, String rating, List<String> primaryResearchFields, List<String> secondaryResearchFields,List<String> specialisations) {
        this.id = id;
        this.surname = surname;
        this.initials = initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
        this.primaryResearchFields=primaryResearchFields;
        this.secondaryResearchFields=secondaryResearchFields;
        this.specialisations=specialisations;
    }

    @Override
    public String toString() {
        return "NrfAuthor{" +
                "surname='" + surname + '\'' +
                ", id='" + id + '\'' +
                ", initials='" + initials + '\'' +
                ", title='" + title + '\'' +
                ", institution='" + institution + '\'' +
                ", rating='" + rating + '\'' +
                ", primaryResearchFields=" + primaryResearchFields +
                ", secondaryResearchFields=" + secondaryResearchFields +
                ", specialisations=" + specialisations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NrfAuthor author = (NrfAuthor) o;
        return Objects.equals(surname, author.surname) && Objects.equals(id, author.id) && Objects.equals(initials, author.initials) && Objects.equals(title, author.title) && Objects.equals(institution, author.institution) && Objects.equals(rating, author.rating) && Objects.equals(primaryResearchFields, author.primaryResearchFields) && Objects.equals(secondaryResearchFields, author.secondaryResearchFields) && Objects.equals(specialisations, author.specialisations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, id, initials, title, institution, rating, primaryResearchFields, secondaryResearchFields, specialisations);
    }
}
