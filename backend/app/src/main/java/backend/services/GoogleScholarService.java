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

import java.util.*;
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
     * 1. Visit scholar.google.com with query param q = "{authorTitle} {authorSurname} {institution}"
     * 2. From the list of articles (not author profiles) pick the first article and follow the author with
     * text "*{authorSurname}". We should end up on the author profile.
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
        final Map<NrfAuthor, Document> authorToProfileDocs = getAuthorProfileDocsParallel(authors);
        logger.log(Level.INFO, "Got all author profiles.");

        logger.log(Level.INFO, "Fetching publications in parallel...");
        final Map<NrfAuthor, List<GoogleScholarPublication>> authorsToPublications = listPublicationsParallel(authors, authorToProfileDocs);
        logger.log(Level.INFO, "Got all author publications.");

        logger.log(Level.INFO, "Putting data together...");
        for(final NrfAuthor author: authors) {
            List<GoogleScholarPublication> publications = authorsToPublications.get(author);

            final Document authorProfileDoc = authorToProfileDocs.get(author);
            List<String> subfields = new LinkedList<>();
            if(authorProfileDoc != null) {
                subfields = listSubFields(authorProfileDoc);
            }

            final GoogleScholarAuthorProfile profile = new GoogleScholarAuthorProfile(subfields, publications);
            out.add(profile);
        }
        logger.log(Level.INFO, "Done putting data together.");

        return out;
    }

    private Document getAuthorProfileDocTolerant(NrfAuthor author, int pageStart) throws NoGoogleScholarProfileException {
        // First search for author with initials (specific).
        // If not found search for author excluding initials (less specific).
        try {
            return getAuthorProfileDoc(author, pageStart, true);
        } catch (NoGoogleScholarProfileException e) {
            return getAuthorProfileDoc(author, pageStart, false);
        }
    }

    private Document getAuthorProfileDoc(NrfAuthor author, int pageStart, boolean addInitials) throws NoGoogleScholarProfileException {
        String q = String.format("%s %s %s", author.title, author.surname, author.institution);
        if(addInitials) {
            q = String.format("%s %s %s %s", author.title, author.initials, author.surname, author.institution);
        }

        q = q.replaceAll(" ", "+");
        final String searchPath = "https://scholar.google.com/scholar?hl=en&as_sdt=0%2C5&q=" + q + "&btnG";

        final String webPage = client.fetchWebPage(searchPath);
        final Document doc = Jsoup.parse(webPage);
        final Elements containersOfAuthorListElements = doc.getElementsByClass("gs_a");
        if(containersOfAuthorListElements.isEmpty()) {
            // No results for given query page.
            throw new NoGoogleScholarProfileException(author);
        }

        // Sometimes the first results has an accent and therefore not marked bold since the
        // Nrf list surnames do not always include accents.

        for(final Element containerOfAuthorListElements: containersOfAuthorListElements) {
            final Elements authorListElements = containerOfAuthorListElements.children();

            for (Element el : authorListElements) {
                if (el.html().contains("<b>")) {
                    final String authorProfileLink = el.attr("href");
                    final String fullAuthorProfileLink = "https://scholar.google.com" + authorProfileLink + String.format("&cstart=%d&pagesize=%d", pageStart, GoogleScholarService.MAX_PUBLICATIONS_PER_SCHOLAR_AUTHOR_PROFILE);
                    return Jsoup.parse(client.fetchWebPage(fullAuthorProfileLink));
                }
            }
        }

        throw new NoGoogleScholarProfileException(author);
    }
    private Map<NrfAuthor, List<GoogleScholarPublication>> listPublicationsParallel(List<NrfAuthor> authors, Map<NrfAuthor, Document> authorToProfileDocs) {
        final Map<NrfAuthor, List<GoogleScholarPublication>> out = new HashMap<>();

        final int workPerThread = computeWorkPerThread(authors.size());
        final List<List<NrfAuthor>> authorPartitions = Lists.partition(authors, workPerThread);

        final List<PublicationsWorker> publicationsWorkers = new LinkedList<>();
        for(int iii = 0; iii < authorPartitions.size(); iii++) {
            final PublicationsWorker worker = new PublicationsWorker(authorPartitions.get(iii), authorToProfileDocs);
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

    private Map<NrfAuthor, Document> getAuthorProfileDocsParallel(List<NrfAuthor> authors) {
        final Map<NrfAuthor, Document> out = new HashMap<>();

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
                out.putAll(worker.authorToProfileDocs);
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
            try {
                authorProfileDoc = getAuthorProfileDocTolerant(author, pageStart);
            } catch (NoGoogleScholarProfileException e) {
                logger.log(Level.INFO, String.format("Author %s, does not have a Google Scholar Profile", author.surname));
            }

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
        try {
            final Elements publicationElements = authorProfileDoc.getElementById("gsc_a_b").children();
            final List<GoogleScholarPublication> out = new LinkedList<>();
            for (final Element tr : publicationElements) {
                final Element titleElement = tr.children().first().children().first();
                final Element citationsElement = tr.child(1);
                final Element yearElement = tr.children().last().children().first();

                int numberOfCitations = 0;
                String citationsText = citationsElement.text().replace("*", "");
                if(!citationsText.isEmpty()) {
                    numberOfCitations = Integer.parseInt(citationsText);
                }

                out.add(new GoogleScholarPublication(new Publication("", numberOfCitations, titleElement.text(), yearElement.text(), "https://scholar.google.com" + titleElement.attr("href")), new LinkedList<>()));
            }

            return out;
        } catch(NullPointerException e) {
            final String s = authorProfileDoc.html();
            throw e;
        }
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
        final Map<NrfAuthor, Document> authorToProfileDocs;

        final public Map<NrfAuthor, List<GoogleScholarPublication>> publications = new HashMap<>();

        public PublicationsWorker(List<NrfAuthor> authors, Map<NrfAuthor, Document> authorToProfileDocs) {
            this.authors = authors;
            this.authorToProfileDocs = authorToProfileDocs;
        }

        @Override
        public void run() {
            for (NrfAuthor author : authors) {
                logger.log(Level.INFO, String.format("fetching publications for %s...", author.surname));
                List<GoogleScholarPublication> googleScholarPublications = new LinkedList<>();
                final Document authorProfileDoc = authorToProfileDocs.get(author);
                if (authorProfileDoc != null) {
                    googleScholarPublications = listPublications(author, authorProfileDoc);
                }
                publications.put(author, googleScholarPublications);
                logger.log(Level.INFO, String.format("Got publications for %s...", author.surname));
            }
        }
    }

    private class ProfileDocWorker extends Thread {
        final List<NrfAuthor> authors;

        final public Map<NrfAuthor, Document> authorToProfileDocs = new HashMap<>();

        public ProfileDocWorker(List<NrfAuthor> authors) {
            this.authors = authors;
        }

        @Override
        public void run() {
            for (NrfAuthor author : authors) {
                try {
                    final Document profileDoc = getAuthorProfileDocTolerant(author, 0);
                    authorToProfileDocs.put(author, profileDoc);
                } catch(NoGoogleScholarProfileException e) {
                    logger.log(Level.INFO, String.format("Author %s, does not have a Google Scholar Profile", author.surname));
                }
            }
        }
    }

    private static class NoGoogleScholarProfileException extends Exception {
        final NrfAuthor author;
        public NoGoogleScholarProfileException(NrfAuthor author) {
            this.author = author;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NoGoogleScholarProfileException that = (NoGoogleScholarProfileException) o;
            return Objects.equals(author, that.author);
        }

        @Override
        public int hashCode() {
            return Objects.hash(author);
        }

        @Override
        public String toString() {
            return "NoGoogleScholarProfileException{" +
                    "author=" + author +
                    '}';
        }
    }
}
