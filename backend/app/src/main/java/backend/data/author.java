package backend.data;
import java.util.*;
public class author {
    private String Surname;
    private String Initials;
    private String Title;
    private String Institution;
    private String Rating;

    public author(String surname, String initials, String title, String institution, String rating) {
        this.Surname = surname ;
        this.Initials = initials;
        this.Title = title;
        this.Institution = institution;
        this.Rating = rating;
    }
    public String getName()
    {
        return this.Surname;
    }

    public String getInitials()
    {
        return this.Initials;
    }
    public String getTitle()
    {
        return this.Title;
    }

    public String getInstitution()
    {
        return this.Institution;
    }

    public String getRating()
    {
        return this.Rating;
    }

   // public String toString(){
   //     return Surname+" "+Initials+" "+Title+" "+""
   // }

}
