package backend.ApplicationModels;

import backend.DatabaseModels.Institution;

public class PopulatedInstitutions {
    public final Institution institution;
    public final int noAuthors;
    public final int noPublications;

    public PopulatedInstitutions(Institution institution,int noAuthors, int noPublications){
        this.institution=institution;
        this.noAuthors=noAuthors;
        this.noPublications=noPublications;
    }
}
