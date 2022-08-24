package backend.models;

public class AuthorToSubfield {
    public String authorId;
    public String subfieldId;

    public AuthorToSubfield(String authorId, String subfieldId){
        this.authorId=authorId;
        this.subfieldId=subfieldId;
    }
}
