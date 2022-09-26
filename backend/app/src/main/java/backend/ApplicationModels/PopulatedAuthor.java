package backend.ApplicationModels;

import backend.DatabaseModels.Institution;
import backend.DatabaseModels.Subfield;

import java.util.List;

public class PopulatedAuthor {

    public String surname;
    public String id;
    public String initials;
    public String title;
    public Institution institution;
    public String rating;
    public List<String> subfields;

    public PopulatedAuthor(String id, String surname, String initials, String title, Institution institution,
            String rating, List<String> subfields) {

        this.surname = surname;
        this.initials = initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
        this.id = id;
        this.subfields = subfields;
    }

}
