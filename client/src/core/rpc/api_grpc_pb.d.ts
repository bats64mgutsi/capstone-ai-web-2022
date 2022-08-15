// package: backend
// file: api.proto

/* tslint:disable */
/* eslint-disable */

import * as grpc from "@grpc/grpc-js";
import * as api_pb from "./api_pb";

interface IAuthorServiceService extends grpc.ServiceDefinition<grpc.UntypedServiceImplementation> {
    list: IAuthorServiceService_Ilist;
}

interface IAuthorServiceService_Ilist extends grpc.MethodDefinition<api_pb.ListAuthorsRequest, api_pb.ListAuthorsResponse> {
    path: "/backend.AuthorService/list";
    requestStream: false;
    responseStream: false;
    requestSerialize: grpc.serialize<api_pb.ListAuthorsRequest>;
    requestDeserialize: grpc.deserialize<api_pb.ListAuthorsRequest>;
    responseSerialize: grpc.serialize<api_pb.ListAuthorsResponse>;
    responseDeserialize: grpc.deserialize<api_pb.ListAuthorsResponse>;
}

export const AuthorServiceService: IAuthorServiceService;

export interface IAuthorServiceServer extends grpc.UntypedServiceImplementation {
    list: grpc.handleUnaryCall<api_pb.ListAuthorsRequest, api_pb.ListAuthorsResponse>;
}

export interface IAuthorServiceClient {
    list(request: api_pb.ListAuthorsRequest, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
    list(request: api_pb.ListAuthorsRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
    list(request: api_pb.ListAuthorsRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
}

export class AuthorServiceClient extends grpc.Client implements IAuthorServiceClient {
    constructor(address: string, credentials: grpc.ChannelCredentials, options?: Partial<grpc.ClientOptions>);
    public list(request: api_pb.ListAuthorsRequest, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
    public list(request: api_pb.ListAuthorsRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
    public list(request: api_pb.ListAuthorsRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: api_pb.ListAuthorsResponse) => void): grpc.ClientUnaryCall;
}
