package backend.DatabaseModels;

import java.util.Objects;

public class Author {
    public String surname;
    public String id;
    public String initials;
    public String title;
    public String institution;
    public String rating;

    public Author(String id,String surname, String initials, String title, String institution, String rating) {
        
        this.surname = surname;
        this.initials = initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(surname, author.surname) && Objects.equals(id, author.id) && Objects.equals(initials, author.initials) && Objects.equals(title, author.title) && Objects.equals(institution, author.institution) && Objects.equals(rating, author.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, id, initials, title, institution, rating);
    }

    @Override
    public String toString() {
        return "Author{" +
                "surname='" + surname + '\'' +
                ", id='" + id + '\'' +
                ", initials='" + initials + '\'' +
                ", title='" + title + '\'' +
                ", institution='" + institution + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}

    