package backend.utils;

import backend.DatabaseModels.Subfield;

import java.util.List;

public class FilteringUtils {

    /**
     * Returns true if the list of subfields contains at least one 1 field
     * whose name is also in the of list.
     */
    public static boolean hasIntersectionIgnoreCase(List<Subfield> subfields, List<String> of) {
        final List<String> lowerCaseOf = of.stream().map(String::toLowerCase).toList();
        return subfields.stream().map(el -> el.name.toLowerCase()).anyMatch(lowerCaseOf::contains);
    }
}
