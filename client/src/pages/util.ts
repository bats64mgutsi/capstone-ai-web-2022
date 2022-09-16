import store from "../store";
import { backendClient } from "../api";
import { UploadNrfResearchersArgs } from "../core";

export const refreshData = async () => {
    if (!store.authors.value.length) {
        const authors = await backendClient().getAuthors();
        store.setAuthors(authors);
    }

    if (store.currentlySelectedAuthorId.value) {
        const profile = await backendClient().getAuthorProfile(store.currentlySelectedAuthorId.value);
        store.setCurrentlySelectedAuthorProfile(profile);
    }
}

export const uploadNrfResearchers = async (data: UploadNrfResearchersArgs) => {
    await backendClient().uploadNrfResearchers(data);
}