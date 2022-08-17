package backend.models;

public class Publication {
    private final int numberOfCitations;
    private final String title;
    private final String year;
    private final String externalLink;

    public Publication(int numberOfCitations, String title, String year, String externalLink) {
        this.numberOfCitations = numberOfCitations;
        this.title = title;
        this.year = year;
        this.externalLink = externalLink;
    }
}
