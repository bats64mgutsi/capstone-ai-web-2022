import { Author } from "../types";

export interface IBackendClient {
    getAuthors(): Author[];
}