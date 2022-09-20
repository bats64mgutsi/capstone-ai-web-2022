import { 
  Author, 
  AuthorProfile, 
  UploadNrfResearchersArgs, 
  NrfResearcher, 
  AdminLoginArgs, 
  LoginResult,
  InstitutionStat,
  SubfieldStat,
  CommunityStat
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
    return await this.client.get<InstitutionStat[]>(`/institutions/stats`);
  }
  
  async getSubfieldStats(): Promise<SubfieldStat[]> {
    return await this.client.get<SubfieldStat[]>(`/subfields/stats`);
  }
  
  
  async getCommunityStats(): Promise<CommunityStat[]> {
    return await this.client.get<CommunityStat[]>(`/community/stats`);
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
