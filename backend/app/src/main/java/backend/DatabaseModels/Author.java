package backend.DatabaseModels;

public class Author {
    public String surname;
    public String id;
    public String initials;
    public String title;
    public String institution;
    public String rating;

    public Author(String id, String surname, String initials, String title, String institution, String rating) {
        this.id = id;
        this.surname = surname;
        this.initials = initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
    }
}

    