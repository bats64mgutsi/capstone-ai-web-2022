package backend.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuth {
    public static String[] getUsernameAndPassword(String basicAuthString) {
        final String encodedPart = basicAuthString.substring(basicAuthString.indexOf(" ") + 1);
        final byte[] decodedBytes = Base64.getDecoder().decode(encodedPart.getBytes());
        final String decodedStr = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedStr.split(":");
    }
}
