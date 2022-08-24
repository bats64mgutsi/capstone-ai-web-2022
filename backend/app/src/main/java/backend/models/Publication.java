package backend.models;

public class Publication {
    public String id;
    public final int citationCount;
    public final String title;
    public final String year;
    public final String externalLink;

    public Publication(String id, int numberOfCitations, String title, String year, String externalLink) {
        this.id =id;
        this.citationCount = numberOfCitations;
        this.title = title;
        this.year = year;
        this.externalLink = externalLink;
    }
}
