export interface Author {
    id: number;
    surname: string;
    initials: string;
    title: string;
    institution: string;
    rating: string;
}

export interface User {
    _id: string;
    name: string;
    email: string;
    title: string;
    birthdate: Date;
    isAdmin: boolean;
    createdAt: Date;
    updatedAt: Date;
}

export interface Publication {
    title: string;
    numberOfCitations: number;
    year: string;
    externalLink: string;
}

export interface AuthorProfile {
    author: Author;
    subFields: string[];
    publications: Publication[];
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
    day: number;
    month: number;
    year: number;
    data: NrfResearcher[]
}