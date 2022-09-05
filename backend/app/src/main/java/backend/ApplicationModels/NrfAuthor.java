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

   // public static NrfAuthor fromJson(Map<String,Object>json){

    //}
}
