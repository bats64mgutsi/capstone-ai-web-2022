import { 
  Author, 
  AuthorProfile, 
  UploadNrfResearchersArgs, 
  NrfResearcher, 
  AdminLoginArgs,
  InstitutionStat,
  SubfieldStat,
  CommunityStat,
  OverallStat
} from "../types";
import { IHttpClient, IBackendClient } from "./types";

export class BackendClient implements IBackendClient {
  private client: IHttpClient;

  constructor(httpClient: IHttpClient) {
    this.client = httpClient;
  }

  async getAuthors(): Promise<Author[]> {
    return await this.client.get<Author[]>(`/authors/`);
  }

  async getInstitutionStats(): Promise<InstitutionStat[]> {
    return await this.client.get<InstitutionStat[]>(`/institutions`);
  }
  
  async getSubfieldStats(): Promise<SubfieldStat[]> {
    return await this.client.get<SubfieldStat[]>(`/subfields`);
  }
  
  
  async getCommunityStats(): Promise<CommunityStat[]> {
    return await this.client.get<CommunityStat[]>(`/community/stats`);
  }

  async getAIFilters(): Promise<string[]> {
    return await this.client.get<string[]>(`/filters`);
  }

  async getAuthorProfile(authorId: string): Promise<AuthorProfile> {
    return await this.client.get<AuthorProfile>(`/author/${authorId}`);
  }

  async getOverallStats(): Promise<OverallStat> {
    return await this.client.get<OverallStat>('/stats');
  }

  async uploadNrfResearchers(args: UploadNrfResearchersArgs): Promise<NrfResearcher[]> {
    return await this.client.post<NrfResearcher[], NrfResearcher[]>(
      `/nrfList/`, 
      args.data, 
      {
        Year: args.year
      }
    );
  }

  async login(args: AdminLoginArgs): Promise<void> {
    await this.client.post<AdminLoginArgs, null>("/validate", args);
  }

  async modifyFilters(filters: string[]): Promise<string[]> {
    return await this.client.post<string[], string[]>("/setFilters", filters);
  }
}
