package backend.DatabaseModels;

import java.util.Objects;

public class Subfield {
    public String id;
    public String name;

    public Subfield(String id, String name)
    {
        this.id=id;
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subfield subfield = (Subfield) o;
        return Objects.equals(id, subfield.id) && Objects.equals(name, subfield.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Subfield{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
