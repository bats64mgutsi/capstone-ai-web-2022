package backend.ApplicationModels;

public class Stats {
    int noAuthorsCurrentYear;
    int noAuthorsPreviousYear;
    int noPublicationsCurrentYear;
    int noPublicationsPreviousYear;
    int noCitationsCurrentYear;
    int noCitationsPreviousYear;
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

    public Stats(int noAuthorsCurrentYear, int noAuthorsPreviousYear, int noPublicationsCurrentYear,
            int noPublicationsPreviousYear, int noCitationsCurrentYear, int noCitationsPreviousYear,
            int ratedACurrentYear,
            int ratedBCurrentYear, int ratedCCurrentYear, int ratedPCurrentYear, int ratedYCurrentYear,
            int ratedAPreviousYear, int ratedBPreviousYear, int ratedCPreviousYear, int ratedPPreviousYear,
            int ratedYPreviousYear) {
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
        this.ratedCPreviousYear = ratedCPreviousYear;

        this.ratedPCurrentYear = ratedPCurrentYear;
        this.ratedPPreviousYear = ratedPPreviousYear;

        this.ratedYCurrentYear = ratedYCurrentYear;
        this.ratedYPreviousYear = ratedYPreviousYear;
    }
}
