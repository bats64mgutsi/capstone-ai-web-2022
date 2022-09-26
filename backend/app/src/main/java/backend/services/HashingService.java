package backend.services;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HashingService {
    public String sha256(String text) {
        return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    }

    public String flatten(List<String> units) {
        return units.stream().map(el -> el.toLowerCase().replaceAll("\\s", "_")).collect(Collectors.joining("_"));
    }
    public String flatten(String unit) {
        List<String> units = new ArrayList<>();
        units.add(unit);
        String unit2 = flatten(units);
        return unit2;
    }
}
