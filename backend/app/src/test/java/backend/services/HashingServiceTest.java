package backend.services;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HashingServiceTest {
    @Test
    public void sha256_shouldReturnSha256HashOfText() {
        final HashingService instance = new HashingService();

        // Used (https://emn178.github.io/online-tools/sha256.html) to generate test hash.
        assertEquals("35072c1ae546350e0bfa7ab11d49dc6f129e72ccd57ec7eb671225bbd197c8f1", instance.sha256("hello_world"));
    }

    @Test
    public void flatten_shouldReturnEmptyStringOnEmptyList() {
        final HashingService instance = new HashingService();
        assertEquals("", instance.flatten(new LinkedList<>()));
    }

    @Test
    public void flatten_shouldReturnWordsInLowerCase() {
        final HashingService instance = new HashingService();
        final List<String> input = new ImmutableList.Builder<String>().add("Abc", "DEF").build();
        assertEquals("abc_def", instance.flatten(input));
    }

    @Test
    public void flatten_shouldReplaceWhitespacesWithUnderscore() {
        final HashingService instance = new HashingService();
        final List<String> input = new ImmutableList.Builder<String>().add("a\nb\tc d").build();
        assertEquals("a_b_c_d", instance.flatten(input));
    }
}
