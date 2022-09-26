package backend.ApplicationModels;

import backend.DatabaseModels.Subfield;

import java.util.Objects;

public class PopulatedSubfield {
    public final Subfield subfield;
    public final int numberOfAuthorsCurrentYear;
    public final int numberOfAuthorsPrevYear;

    public PopulatedSubfield(Subfield subfield, int numberOfAuthorsCurrentYear, int numberOfAuthorsPrevYear) {
        this.subfield = subfield;
        this.numberOfAuthorsCurrentYear = numberOfAuthorsCurrentYear;
        this.numberOfAuthorsPrevYear = numberOfAuthorsPrevYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopulatedSubfield that = (PopulatedSubfield) o;
        return numberOfAuthorsCurrentYear == that.numberOfAuthorsCurrentYear && numberOfAuthorsPrevYear == that.numberOfAuthorsPrevYear && Objects.equals(subfield, that.subfield);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subfield, numberOfAuthorsCurrentYear, numberOfAuthorsPrevYear);
    }

    @Override
    public String toString() {
        return "PopulatedSubfield{" +
                "subfield=" + subfield +
                ", numberOfAuthorsCurrentYear=" + numberOfAuthorsCurrentYear +
                ", numberOfAuthorsPrevYear=" + numberOfAuthorsPrevYear +
                '}';
    }
}
