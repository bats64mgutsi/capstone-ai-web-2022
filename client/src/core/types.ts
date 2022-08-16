export interface Author {
    id: number;
    name: string;
    initials: string;
    title: string;
    insitution: string;
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

export interface AppMessage {
    type: string;
    message: string;
}

export interface LoginResult {
    user: User;
    token: string;
}