import { Author, AuthorProfile, UploadNrfResearchersArgs, NrfResearcher, AdminLoginArgs, LoginResult } from "../types";
import { IHttpClient, IBackendClient } from "./types";

export class BackendClient implements IBackendClient {
  private client: IHttpClient;

  constructor(httpClient: IHttpClient) {
    this.client = httpClient;
  }

  async getAuthors(): Promise<Author[]> {
    return await this.client.get<Author[]>(`/authors/`);
  }

  async getAuthorProfile(authorId: string): Promise<AuthorProfile> {
    return await this.client.get<AuthorProfile>(`/author/${authorId}`);
  }

  async uploadNrfResearchers(args: UploadNrfResearchersArgs): Promise<NrfResearcher[]> {
    return await this.client.post<NrfResearcher[], NrfResearcher[]>(
      `/nrfList/`, 
      args.data, 
      {
        
      }
    );
  }

  async login(args: AdminLoginArgs): Promise<LoginResult> {
    return await this.client.post<AdminLoginArgs, LoginResult>("/admin/login", args);
  }
}
