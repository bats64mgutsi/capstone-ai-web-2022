package backend.services;

import backend.ApplicationModels.GoogleScholarAuthorProfile;
import backend.ApplicationModels.GoogleScholarPublication;
import backend.ApplicationModels.NrfAuthor;
import backend.DatabaseModels.Publication;
import backend.Locator;
import backend.httpClient.HttpClient;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleScholarService {

    final Logger logger = Logger.getLogger(GoogleScholarService.class.getName());

    private static final int MAX_PUBLICATIONS_PER_SCHOLAR_AUTHOR_PROFILE = 100;

    final HttpClient client = (HttpClient) Locator.instance.get(HttpClient.class);

    /**
     * Scrapes Google Scholar for the given authors profiles.
     * <p>
     * Scraping is done as follows:
     * 1. Visit scholar.google.com with query param q = "{authorInitials} {authorSurname} {institution}"
     * 2. From the list of articles (not author profiles) pick the first article and follow the author with
     * text "{authorInitials} {authorSurname}". We should end up on the author profile.
     * 3. Take all the author publications showing. This list is paginated, and so we keep paginating it
     *    until the "Show More" button is disabled which means there are no more pages.
     * 4. From the author profile extract all the subfields the author is into.
     * 4. Do this for all authors and return the final list of profiles.
     * <p>
     *
     * NB: We ignore the author profiles list given by Google Scholar as for some authors it does now show.
     * Just to be consistent, we ignore it completely and assume it is not showing.
     *
     * @param authors is the list of authors to get publications for.
     * @return the list of author profiles.
     */
    public List<GoogleScholarAuthorProfile> fetchProfiles(List<NrfAuthor> authors) {
        final List<GoogleScholarAuthorProfile> out = new LinkedList<>();

        logger.log(Level.INFO, "Fetching author profiles in parallel...");
        final List<Document> authorProfileDocs = getAuthorProfileDocsParallel(authors);
        logger.log(Level.INFO, "Got all author profiles.");

        logger.log(Level.INFO, "Fetching publications in parallel...");
        final Map<NrfAuthor, List<GoogleScholarPublication>> authorsToPublications = listPublicationsParallel(authors, authorProfileDocs);
        logger.log(Level.INFO, "Got all author publications.");

        logger.log(Level.INFO, "Putting data together...");
        for(int iii = 0; iii < authors.size(); iii++) {
            final NrfAuthor author = authors.get(iii);
            final Document authorProfileDoc = authorProfileDocs.get(iii);
            final List<GoogleScholarPublication> publications = authorsToPublications.get(author);
            final List<String> subfields = listSubFields(authorProfileDoc);

            final GoogleScholarAuthorProfile profile = new GoogleScholarAuthorProfile(subfields, publications);
            out.add(profile);
        }
        logger.log(Level.INFO, "Done putting data together.");

        return out;
    }

    private Document getAuthorProfileDoc(NrfAuthor author, int pageStart) {
        final String q = String.format("%s %s %s", author.initials, author.surname, author.institution).replaceAll(" ", "+");
        final String searchPath = "https://scholar.google.com/scholar?hl=en&as_sdt=0%2C5&q=" + q + "&btnG";

        final Document doc = Jsoup.parse(client.fetchWebPage(searchPath));
        final Elements authorListElements = doc.getElementsByClass("gs_a").first().children();

        String authorProfileLink = null;
        for (Element el : authorListElements) {
            if (el.html().contains("<b>")) {
                authorProfileLink = el.attr("href");
                break;
            }
        }

        final String fullAuthorProfileLink = "https://scholar.google.com" + authorProfileLink + String.format("&cstart=%d&pagesize=%d", pageStart, GoogleScholarService.MAX_PUBLICATIONS_PER_SCHOLAR_AUTHOR_PROFILE);
        return Jsoup.parse(client.fetchWebPage(fullAuthorProfileLink));
    }

    private Map<NrfAuthor, List<GoogleScholarPublication>> listPublicationsParallel(List<NrfAuthor> authors, List<Document> authorProfileDocs) {
        final Map<NrfAuthor, List<GoogleScholarPublication>> out = new HashMap<>();

        final int workPerThread = computeWorkPerThread(authors.size());
        final List<List<NrfAuthor>> authorPartitions = Lists.partition(authors, workPerThread);
        final List<List<Document>> authorProfileDocsPartitions = Lists.partition(authorProfileDocs, workPerThread);

        final List<PublicationsWorker> publicationsWorkers = new LinkedList<>();
        for(int iii = 0; iii < authorPartitions.size(); iii++) {
            final PublicationsWorker worker = new PublicationsWorker(authorPartitions.get(iii), authorProfileDocsPartitions.get(iii));
            worker.start();
            publicationsWorkers.add(worker);
        }

        for(final PublicationsWorker worker: publicationsWorkers) {
            try {
                worker.join();
                out.putAll(worker.publications);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return out;
    }

    private List<Document> getAuthorProfileDocsParallel(List<NrfAuthor> authors) {
        final List<Document> out = new LinkedList<>();

        final int workPerThread = computeWorkPerThread(authors.size());
        final List<List<NrfAuthor>> authorPartitions = Lists.partition(authors, workPerThread);

        final List<ProfileDocWorker> profileDocWorkers = new LinkedList<>();
        for(int iii = 0; iii < authorPartitions.size(); iii++) {
            final ProfileDocWorker worker = new ProfileDocWorker(authorPartitions.get(iii));
            worker.start();
            profileDocWorkers.add(worker);
        }

        for(final ProfileDocWorker worker: profileDocWorkers) {
            try {
                worker.join();
                out.addAll(worker.authorProfileDocs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return out;
    }

    private List<GoogleScholarPublication> listPublications(NrfAuthor author, Document authorProfileDoc) {
        final List<GoogleScholarPublication> out = new LinkedList<>();
        out.addAll(extractPublicationsFromProfileDoc(authorProfileDoc));

        int pageStart = 100;
        boolean hasMorePublications = hasMorePublications(authorProfileDoc);
        while(hasMorePublications) {
            authorProfileDoc = getAuthorProfileDoc(author, pageStart);
            out.addAll(extractPublicationsFromProfileDoc(authorProfileDoc));

            hasMorePublications = hasMorePublications(authorProfileDoc);
            pageStart += 100;
        }

        return out;
    }

    private boolean hasMorePublications(Document authorProfileDoc) {
        final Element moreButton = authorProfileDoc.getElementById("gsc_bpf_more");
        return !moreButton.hasAttr("disabled");
    }

    private List<GoogleScholarPublication> extractPublicationsFromProfileDoc(Document authorProfileDoc) {
        final Elements publicationElements = authorProfileDoc.getElementById("gsc_a_b").children();
        final List<GoogleScholarPublication> out = new LinkedList<>();
        for (final Element tr : publicationElements) {
            final Element titleElement = tr.children().first().children().first();
            final Element citationsElement = tr.child(1);
            final Element yearElement = tr.children().last().children().first();

            int numberOfCitations = -1;
            String citationsText = citationsElement.text().replace("*", "");
            if(!citationsText.isEmpty()) {
                numberOfCitations = Integer.parseInt(citationsText);
            }

            out.add(new GoogleScholarPublication(new Publication("", numberOfCitations, titleElement.text(), yearElement.text(), "https://scholar.google.com" + titleElement.attr("href")), new LinkedList<>()));
        }

        return out;
    }

    private List<String> listSubFields(Document authorProfileDoc) {
        final Element listElement = authorProfileDoc.getElementById("gsc_prf_int");
        return listElement.children().stream().map(el -> el.text()).toList();
    }

    private int computeWorkPerThread(int n) {
        final int numOfThreads = Runtime.getRuntime().availableProcessors();

        // Let each thread handle 1 work since it takes seconds to complete 1 work.
        if(n <= numOfThreads) return 1;

        return (int) Math.ceil(n/numOfThreads);
    }

    private class PublicationsWorker extends Thread {
        final List<NrfAuthor> authors;
        final List<Document> authorProfileDocs;

        final public Map<NrfAuthor, List<GoogleScholarPublication>> publications = new HashMap<>();

        public PublicationsWorker(List<NrfAuthor> authors, List<Document> authorProfileDocs) {
            this.authors = authors;
            this.authorProfileDocs = authorProfileDocs;
        }

        @Override
        public void run() {
            for(int iii = 0; iii < authors.size(); iii++) {
                final List<GoogleScholarPublication> googleScholarPublications = listPublications(authors.get(iii), authorProfileDocs.get(iii));
                publications.put(authors.get(iii), googleScholarPublications);
            }
        }
    }

    private class ProfileDocWorker extends Thread {
        final List<NrfAuthor> authors;

        final public List<Document> authorProfileDocs = new LinkedList<>();

        public ProfileDocWorker(List<NrfAuthor> authors) {
            this.authors = authors;
        }

        @Override
        public void run() {
            for (NrfAuthor author : authors) {
                final Document profileDoc = getAuthorProfileDoc(author, 0);
                authorProfileDocs.add(profileDoc);
            }
        }
    }
}
