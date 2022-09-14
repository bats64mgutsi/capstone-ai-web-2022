package backend.services;

import backend.ApplicationModels.GoogleScholarPublication;
import backend.DatabaseModels.Publication;
import backend.Locator;
import backend.httpClient.HttpClient;
import com.google.common.collect.ImmutableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class GoogleScholarService {
    final HttpClient client = (HttpClient) Locator.instance.get(HttpClient.class);

    /**
     * Crawls GoogleScholar for all the articles for the author with the given details.
     * <p>
     * Crawling is done as follows:
     * 1. Visit scholar.google.com with query param q = "{authorInitials} {authorSurname} {institution}"
     * 2. From the list of articles (not author profiles) pick teh first article and follow the author with
     * text "{authorInitials} {authorSurname}". We should end up on our author profile.
     * 3. Take all the author publications showing. This list is paginated. For now, we will take just
     * the first page.
     * 4. Return this list of publications
     * <p>
     * NB: We ignore the author profiles list given by Google Scholar as for some authors it does now show.
     * Just to be consistent, we ignore it completely and assume it is not showing.
     *
     * @param authorInitials the initials of the author as found in the NRF list.
     * @param authorSurname  the surname of the author as found in the NRF list.
     * @param institution    the institution of the author as found in teh NRF list.
     * @return all the publications of the author.
     */
    public List<GoogleScholarPublication> listPublications(String authorInitials, String authorSurname, String institution) {
        final String q = String.format("%s %s %s", authorInitials, authorSurname, institution).replaceAll(" ", "+");
        final String searchPath = "https://scholar.google.com/scholar?hl=en&as_sdt=0%2C5&q=" + q + "&btnG";

        final Document doc = Jsoup.parse(client.fetchWebPage(searchPath));
        final Elements authorListElements = doc.getElementsByClass("gs_a").first().children();

        String authorProfileLink = null;
        for (Element el : authorListElements) {
            final String html = el.html();
            if (el.html().contains("<b>")) {
                authorProfileLink = el.attr("href");
                break;
            }
        }

        final String fullAuthorProfileLink = "https://scholar.google.com" + authorProfileLink;
        final Document authorProfileDoc = Jsoup.parse(client.fetchWebPage(fullAuthorProfileLink));

        final Elements publicationElements = authorProfileDoc.getElementById("gsc_a_b").children();
        final List<GoogleScholarPublication> out = new LinkedList<>();
        for (final Element tr : publicationElements) {
            final Element titleElement = tr.children().first().children().first();
            final Element citationsElement = tr.child(1);
            final Element yearElement = tr.children().last().children().first();

            out.add(new GoogleScholarPublication(new Publication("", Integer.valueOf(citationsElement.text()), titleElement.text(), yearElement.text(), "https://scholar.google.com" + titleElement.attr("href")), new LinkedList<>()));
        }

        return out;
    }

}
