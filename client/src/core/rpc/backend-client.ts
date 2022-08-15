import { IBackendClient } from "./types";
import { Author } from "../types";

export class BackendClient implements IBackendClient {
  constructor() {
    // TODO
  }

  async getAuthors(): Promise<Author[]> {
    // TODO
    return [];
  }
}
