import { 
  Author, 
  AuthorProfile, 
  UploadNrfResearchersArgs, 
  NrfResearcher, 
  AdminLoginArgs, 
  LoginResult,
  InstitutionStat,
  SubfieldStat,
  CommunityStat,
  AIFilterAddArgs,
  AIFilter
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

  async getAIFilters(): Promise<AIFilter[]> {
    return await this.client.get<AIFilter[]>(`/filters`);
  }

  async getAuthorProfile(authorId: string): Promise<AuthorProfile> {
    return await this.client.get<AuthorProfile>(`/author/${authorId}`);
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

  async addFilter(args: AIFilterAddArgs): Promise<AIFilter> {
    return await this.client.post<AIFilterAddArgs, AIFilter>("/filters/add", args);
  }
}
