import store from "../store";
import { backendClient } from "../rpc";

export const refreshData = async () => {
    const authors = await backendClient().getAuthors();
    store.setAuthors(authors);
}