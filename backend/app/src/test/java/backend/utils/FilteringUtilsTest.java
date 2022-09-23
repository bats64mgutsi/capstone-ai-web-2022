package backend.utils;

import backend.DatabaseModels.Subfield;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilteringUtilsTest {

    @Test
    public void hasIntersection_shouldReturnFalseWhenInputsDoNotHaveIntersection() {
        final List<Subfield> subfields = new ImmutableList.Builder<Subfield>()
                .add(new Subfield("", "abc"))
                .add(new Subfield("", "def"))
                .add(new Subfield("", "ghi"))
                .build();

        final List<String> of = new ImmutableList.Builder<String>()
                .add("apple")
                .add("banana")
                .add("carrot")
                .build();

        assertFalse(FilteringUtils.hasIntersectionIgnoreCase(subfields, of));
    }

    @Test
    public void hasIntersection_shouldReturnTrueWhenInputsHaveAtLeastOneIntersection() {
        final List<Subfield> subfields = new ImmutableList.Builder<Subfield>()
                .add(new Subfield("", "aBc"))
                .add(new Subfield("", "def"))
                .add(new Subfield("", "ghi"))
                .build();

        final List<String> of = new ImmutableList.Builder<String>()
                .add("apple")
                .add("Abc")
                .add("carrot")
                .build();

        assertTrue(FilteringUtils.hasIntersectionIgnoreCase(subfields, of));
    }
}
