// package: backend
// file: api.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from "google-protobuf";

export class Author extends jspb.Message { 
    getName(): string;
    setName(value: string): Author;
    getInitials(): string;
    setInitials(value: string): Author;
    getTitle(): string;
    setTitle(value: string): Author;
    getInsitution(): string;
    setInsitution(value: string): Author;
    getProvince(): string;
    setProvince(value: string): Author;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): Author.AsObject;
    static toObject(includeInstance: boolean, msg: Author): Author.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: Author, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): Author;
    static deserializeBinaryFromReader(message: Author, reader: jspb.BinaryReader): Author;
}

export namespace Author {
    export type AsObject = {
        name: string,
        initials: string,
        title: string,
        insitution: string,
        province: string,
    }
}

export class ListAuthorsRequest extends jspb.Message { 

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): ListAuthorsRequest.AsObject;
    static toObject(includeInstance: boolean, msg: ListAuthorsRequest): ListAuthorsRequest.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: ListAuthorsRequest, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): ListAuthorsRequest;
    static deserializeBinaryFromReader(message: ListAuthorsRequest, reader: jspb.BinaryReader): ListAuthorsRequest;
}

export namespace ListAuthorsRequest {
    export type AsObject = {
    }
}

export class ListAuthorsResponse extends jspb.Message { 
    clearAuthorsList(): void;
    getAuthorsList(): Array<Author>;
    setAuthorsList(value: Array<Author>): ListAuthorsResponse;
    addAuthors(value?: Author, index?: number): Author;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): ListAuthorsResponse.AsObject;
    static toObject(includeInstance: boolean, msg: ListAuthorsResponse): ListAuthorsResponse.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: ListAuthorsResponse, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): ListAuthorsResponse;
    static deserializeBinaryFromReader(message: ListAuthorsResponse, reader: jspb.BinaryReader): ListAuthorsResponse;
}

export namespace ListAuthorsResponse {
    export type AsObject = {
        authorsList: Array<Author.AsObject>,
    }
}

export enum AuthorRating {
    A = 0,
    B = 1,
    C = 2,
    P = 3,
    Y = 4,
}
