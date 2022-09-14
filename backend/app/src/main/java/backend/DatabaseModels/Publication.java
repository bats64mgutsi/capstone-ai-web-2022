package backend.DatabaseModels;

import java.util.Objects;

public class Publication {
    public final int citationCount;
    public final String title;
    public final String year;
    public final String externalLink;
    public String id;


    public Publication(String id, int numberOfCitations, String title, String year, String externalLink) {
        this.id = id;
        this.citationCount = numberOfCitations;
        this.title = title;
        this.year = year;
        this.externalLink = externalLink;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "citationCount=" + citationCount +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", externalLink='" + externalLink + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return citationCount == that.citationCount && Objects.equals(title, that.title) && Objects.equals(year, that.year) && Objects.equals(externalLink, that.externalLink) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citationCount, title, year, externalLink, id);
    }
}
