package backend.services;

import backend.ApplicationModels.GoogleScholarPublication;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GoogleScholarServiceTest {

    String scholarAuthorSearch;
    String scholarAuthorProfile;

    @Before
    public void before() throws IOException {
        final Path resourcesDir = Path.of("", "src/test/data");

        Path file = resourcesDir.resolve("scholar_author_profile.html");
        scholarAuthorProfile = IOUtils.toString(new FileReader(file.toFile()));

        file = resourcesDir.resolve("scholar_author_search.html");
        scholarAuthorSearch = IOUtils.toString(new FileReader(file.toFile()));
    }

    @Test
    public void listPublications_shouldReturnFirstListOfAuthorPublications() {
        final HttpClient mockedHttpClient = mock(HttpClient.class);
        Locator.instance.registerSingleton(mockedHttpClient);

        when(mockedHttpClient.fetchWebPage("https://scholar.google.com/scholar?hl=en&as_sdt=0%2C5&q=V+Aharonson+University+of+the+Witwatesrand&btnG")).thenReturn(scholarAuthorSearch);
        when(mockedHttpClient.fetchWebPage("https://scholar.google.com/citations?user=MlXzlYQAAAAJ&hl=en&oi=sra")).thenReturn(scholarAuthorProfile);

        final List<GoogleScholarPublication> extractedPublications = new GoogleScholarService().listPublications("V", "Aharonson", "University of the Witwatesrand");
        final List<GoogleScholarPublication> expectedListContains = new ImmutableList.Builder<GoogleScholarPublication>()
                .add(new GoogleScholarPublication(new Publication("", 241, "The relevance of feature type for the automatic classification of emotional user states: low level descriptors and functionals", "2007", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&citation_for_view=MlXzlYQAAAAJ:zYLM7Y9cAGgC"), new ImmutableList.Builder<String>().build()))
                .add(new GoogleScholarPublication(new Publication("", 50, "Patterns, prototypes, performance: classifying emotional user states", "2008", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&citation_for_view=MlXzlYQAAAAJ:Tyk-4Ss8FVUC"), new ImmutableList.Builder<String>().build()))
                .add(new GoogleScholarPublication(new Publication("", 18, "The response of peripheral microcirculation to gravity-induced changes", "2018", "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&citation_for_view=MlXzlYQAAAAJ:NaGl4SEjCO4C"), new ImmutableList.Builder<String>().build()))
                .build();

        assertEquals(20, extractedPublications.size());
        assertEquals(expectedListContains.get(0), extractedPublications.get(0));
        assertEquals(expectedListContains.get(1), extractedPublications.get(8));
        assertEquals(expectedListContains.get(2), extractedPublications.get(19));
    }
}
