import { Author } from "../types";

export interface IBackendClient {
    getAuthors(): Promise<Author[]>;
}