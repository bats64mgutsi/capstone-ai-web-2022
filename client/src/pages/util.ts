import store from "../store";
import { backendClient } from "../api";

export const refreshData = async () => {
    const authors = await backendClient().getAuthors();
    console.log("returned authors: ", authors);
    store.setAuthors(authors);
}