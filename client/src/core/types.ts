export interface Author {
    id: string;
    surname: string;
    initials: string;
    title: string;
    institution: Institution;
    rating: string;
}

export interface User {
    username: string;
}

export interface Publication {
    title: string;
    numberOfCitations: number;
    year: string;
    externalLink: string;
}

export interface AuthorProfile {
    author: Author;
    subFields: Subfield[];
    publications: Publication[];
}

export interface Institution {
    id: string;
    name: string;
    latitude: number;
    longitude: number;
}

export interface InstitutionStat {
    institution: Institution;
    noAuthors: number;
    noPublications: number;
}

export interface Subfield {
    id: string;
    name: string;
}

export interface SubfieldStat {
    id: string;
    subfield: Subfield;
    numberOfResearchers: number;
    numberOfPublications: number;
}

export enum Rating {
    A = 'A',
    B = 'B',
    C = 'C',
    Y = 'Y',
    P = 'P'
}

export interface CommunityStat {
    id: string;
    rating: Rating;
    numberOfResearchers: number;
    numberOfPublications: number;
}

export interface AppMessage {
    type: string;
    message: string;
}

export interface LoginResult {
    user: User;
    token: string;
}

export interface NrfResearcher {
    surname: string;
    initials: string;
    title: string;
    rating: string;
    primaryResearchFields: string[];
    secondaryResearchFields: string[];
    specialisations: string[];
}

export interface UploadNrfResearchersArgs {
    year: number;
    data: NrfResearcher[]
}

export interface AppMessage {
    type: string;
    message: string;
}

export interface AdminLoginArgs {
    username: string;
    password: string;
}

export interface LoginResult {
    user: User;
    token: string;
}