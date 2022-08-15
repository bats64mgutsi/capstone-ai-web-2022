import store from "../store";
import { backendClient } from "../rpc";
export const refreshData = async () => {
    const authors = await backendClient().getAuthors();
    console.log("returned authors: ", authors);
    store.setAuthors(authors);
};
//# sourceMappingURL=util.js.map