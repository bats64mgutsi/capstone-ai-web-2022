import { 
    Author, 
    AuthorProfile, 
    NrfResearcher, 
    UploadNrfResearchersArgs, 
    AdminLoginArgs, 
    LoginResult, 
    InstitutionStat, 
    CommunityStat, 
    SubfieldStat 
} from "../types";

export interface IHttpClient {
    baseUrl: string;

    get<T>(path: string, headers?: any): Promise<T>;

    post<TBody, TResult>(
        path: string,
        body: TBody,
        headers?: any
    ): Promise<TResult>;

    put<TBody, TResult>(
        path: string,
        body: TBody,
        headers?: any
    ): Promise<TResult>;

    patch<TBody, TResult>(
        path: string,
        body: TBody,
        headers?: any
    ): Promise<TResult>;

    delete<T>(path: string, headers?: any): Promise<T>;

    request<T>(
        path: string,
        method?: string,
        body?: any,
        headers?: any
    ): Promise<T>;
}

export interface IBackendClient {
    getAuthors(): Promise<Author[]>;
    getInstitutionStats(): Promise<InstitutionStat[]>;
    getSubfieldStats(): Promise<SubfieldStat[]>;
    getCommunityStats(): Promise<CommunityStat[]>;
    getAuthorProfile(authorId: string): Promise<AuthorProfile>;
    uploadNrfResearchers(args: UploadNrfResearchersArgs): Promise<NrfResearcher[]>;
    login(args: AdminLoginArgs): Promise<LoginResult>;
}