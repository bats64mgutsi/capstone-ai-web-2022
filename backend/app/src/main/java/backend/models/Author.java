package backend.models;

import java.util.*;

public class Author {
    private String Surname;
    private String Initials;
    private String Title;
    private String Institution;
    private String Rating;

    public Author(String surname, String initials, String title, String institution, String rating) {
        this.Surname = surname;
        this.Initials = initials;
        this.Title = title;
        this.Institution = institution;
        this.Rating = rating;
    }

    public String getName() {
        return this.Surname;
    }

    public String getInitials() {
        return this.Initials;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getInstitution() {
        return this.Institution;
    }

    public String getRating() {
        return this.Rating;
    }

     public Map<String, Object> toJson(){
     final HashMap hm = new HashMap();
     hm.put("surname",Surname)
     hm.put("initials",Initials)
     hm.put("title",Title)
     hm.put("institution",Institution)
     hm.put("rating",Rating)
     
         
     }

}
