package backend.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class BasicAuthTest {

    @Test
    public void getUsernameAndPassword_shouldExtractCorrectCredentials() {
        // Used https://www.base64encode.org tool
        final String input = "BASIC YWJjVXNlcm5hbWU6YWJjUGFzc3dvcmQ=";
        final String[] expectedRet = {"abcUsername", "abcPassword"};

        assertArrayEquals(expectedRet, BasicAuth.getUsernameAndPassword(input));
    }
}
