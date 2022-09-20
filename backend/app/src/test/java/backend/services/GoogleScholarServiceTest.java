package backend.services;

import backend.ApplicationModels.GoogleScholarAuthorProfile;
import backend.ApplicationModels.GoogleScholarPublication;
import backend.ApplicationModels.NrfAuthor;
import backend.DatabaseModels.Publication;
import backend.Locator;
import backend.httpClient.HttpClient;
import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GoogleScholarServiceTest {
    String scholarAuthorSearch;
    String scholarAuthorProfile0_100;
    String scholarAuthorProfile100_100;

    @Before
    public void before() throws IOException {
        final Path resourcesDir = Path.of("", "src/test/data");

        Path file = resourcesDir.resolve("scholar_author_profile_0_100.html");
        scholarAuthorProfile0_100 = IOUtils.toString(new FileReader(file.toFile()));

        file = resourcesDir.resolve("scholar_author_profile_100_100.html");
        scholarAuthorProfile100_100 = IOUtils.toString(new FileReader(file.toFile()));

        file = resourcesDir.resolve("scholar_author_search.html");
        scholarAuthorSearch = IOUtils.toString(new FileReader(file.toFile()));
    }

    @Test
    public void fetchProfiles_shouldReturnAllAuthorsProfiles() {
        final List<String> emptyStringList = new LinkedList<>();
        final NrfAuthor testAuthor = new NrfAuthor("", "Aharonson", "V", "", "University of the Witwatesrand", "", emptyStringList, emptyStringList, emptyStringList);
        final List<NrfAuthor> authors = new ImmutableList.Builder<NrfAuthor>().add(testAuthor).add(testAuthor).build();

        final HttpClient mockedHttpClient = mock(HttpClient.class);
        Locator.instance.registerSingleton(mockedHttpClient);

        when(mockedHttpClient.fetchWebPage("https://scholar.google.com/scholar?hl=en&as_sdt=0%2C5&q=V+Aharonson+University+of+the+Witwatesrand&btnG")).thenReturn(scholarAuthorSearch);
        when(mockedHttpClient.fetchWebPage("https://scholar.google.com/citations?user=MlXzlYQAAAAJ&hl=en&oi=sra&cstart=0&pagesize=100")).thenReturn(scholarAuthorProfile0_100);
        when(mockedHttpClient.fetchWebPage("https://scholar.google.com/citations?user=MlXzlYQAAAAJ&hl=en&oi=sra&cstart=100&pagesize=100")).thenReturn(scholarAuthorProfile100_100);

        final List<String> expectedSubfieldsList = new ImmutableList.Builder<String>().add("Medical Engineering", "Signal Processing").build();
        final List<GoogleScholarPublication> expectedPublicationsListContains = new ImmutableList.Builder<GoogleScholarPublication>()
                .add(new GoogleScholarPublication(new Publication("", 240, "Computer-based, personalized cognitive training versus classical computer games: a randomized double-blind prospective trial of cognitive stimulation", "2011", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&pagesize=100&citation_for_view=MlXzlYQAAAAJ:MXK_kJrjxJIC"), new ImmutableList.Builder<String>().build()))
                .add(new GoogleScholarPublication(new Publication("", 9, "Emotion Elicitation in a Computerized Gambling Game", "", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&pagesize=100&citation_for_view=MlXzlYQAAAAJ:4JMBOYKVnBMC"), new ImmutableList.Builder<String>().build()))
                .add(new GoogleScholarPublication(new Publication("", -1, "2012 IEEE 27 th Convention of Electrical and Electronics Engineers in Israel", "", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&cstart=100&pagesize=100&citation_for_view=MlXzlYQAAAAJ:hqOjcs7Dif8C"), new ImmutableList.Builder<String>().build())).build();

        final List<GoogleScholarAuthorProfile> profiles = new GoogleScholarService().fetchProfiles(authors);

        assertEquals(2, profiles.size());

        assertEquals(expectedSubfieldsList, profiles.get(0).subFields);
        assertEquals(expectedSubfieldsList, profiles.get(1).subFields);

        assertEquals(104, profiles.get(0).publications.size());
        assertEquals(expectedPublicationsListContains.get(0), profiles.get(0).publications.get(0));
        assertEquals(expectedPublicationsListContains.get(1), profiles.get(0).publications.get(32));
        assertEquals(expectedPublicationsListContains.get(2), profiles.get(0).publications.get(103));

        assertEquals(104, profiles.get(1).publications.size());
        assertEquals(expectedPublicationsListContains.get(0), profiles.get(1).publications.get(0));
        assertEquals(expectedPublicationsListContains.get(1), profiles.get(1).publications.get(32));
        assertEquals(expectedPublicationsListContains.get(2), profiles.get(1).publications.get(103));
    }
}
