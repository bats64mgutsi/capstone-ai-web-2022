package backend.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class UrlParserTest {
    @Test
    public void parsePaths_shouldReturnEmptyOnEmptyInput() {
        assertArrayEquals(UrlParser.parsePaths(""), new String[]{});
    }

    @Test
    public void parsePaths_shouldReturnCorrectPaths() {
        assertArrayEquals(UrlParser.parsePaths("/abc/def/ghi"), new String[]{
                "", "abc", "def", "ghi"
        });
    }
}
