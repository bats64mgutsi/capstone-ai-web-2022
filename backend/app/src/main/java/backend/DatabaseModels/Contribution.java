package backend.DatabaseModels;

public class  Contribution {
    public String publicationId;
    public String contributorId;
    public ContributionType type;

    public enum ContributionType{
        MainAuthor,
        CoAuthor
    }
    public Contribution (String publicationId, String contributorId, ContributionType type){
        this.contributorId=contributorId;
        this.publicationId=publicationId;
        this.type=type;
    }
}
