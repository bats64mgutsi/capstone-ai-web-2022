package backend.models;

public class Author {
    private String surname;
    private String initials;
    private String title;
    private String institution;
    private String rating;

    public Author(String surname, String initials, String title, String institution, String rating) {
        this.surname = surname;
        this.initials = initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
    }

    public String getName() {
        return this.surname;
    }

    public String getInitials() {
        return this.initials;
    }

    public String getTitle() {
        return this.title;
    }

    public String getInstitution() {
        return this.institution;
    }

    public String getRating() {
        return this.rating;
    }

}
