package backend.ApplicationModels;

public class Stats {
    int noAuthorsCurrentYear;
    int noAuthorsPreviousYear;
    int noPublicationsCurrentYear;
    int noPublicationsPreviousYear;
    int noCitationsCurrentYear;
    int noCitationsPreviousYear; // in country
    int ratedACurrentYear;
    int ratedBCurrentYear;
    int ratedCCurrentYear;
    int ratedPCurrentYear;
    int ratedYCurrentYear;
    int ratedAPreviousYear;
    int ratedBPreviousYear;
    int ratedCPreviousYear;
    int ratedPPreviousYear;
    int ratedYPreviousYear;
    boolean trend; //false = less than last year

    public Stats(int noAuthorsCurrentYear, int noAuthorsPreviousYear, int noPublicationsCurrentYear,
            int noPublicationsPreviousYear, int noCitationsCurrentYear, int noCitationsPreviousYear,
            int ratedACurrentYear,
            int ratedBCurrentYear, int ratedCCurrentYear, int ratedPCurrentYear, int ratedYCurrentYear,
            int ratedAPreviousYear, int ratedBPreviousYear, int ratedCPreviousYear, int ratedPPreviousYear,
            int ratedYPreviousYear, boolean trend) {
        this.noAuthorsCurrentYear = noAuthorsCurrentYear;
        this.noAuthorsPreviousYear = noAuthorsPreviousYear;
        this.noPublicationsCurrentYear = noPublicationsCurrentYear;
        this.noPublicationsPreviousYear = noPublicationsPreviousYear;

        this.noCitationsCurrentYear = noCitationsCurrentYear;
        this.noCitationsPreviousYear = noCitationsPreviousYear;

        this.ratedACurrentYear = ratedACurrentYear;
        this.ratedAPreviousYear = ratedAPreviousYear;

        this.ratedBPreviousYear = ratedBPreviousYear;
        this.ratedBCurrentYear = ratedBCurrentYear;

        this.ratedCCurrentYear = ratedCCurrentYear;
        this.ratedCCurrentYear = ratedCCurrentYear;

        this.ratedPCurrentYear = ratedPCurrentYear;
        this.ratedPPreviousYear = ratedPPreviousYear;
        this.ratedYPreviousYear = ratedYPreviousYear;
        this.ratedYCurrentYear = ratedYCurrentYear;
        this.trend=trend;
    }
}
