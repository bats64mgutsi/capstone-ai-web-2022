// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var api_pb = require('./api_pb.js');

function serialize_backend_ListAuthorsRequest(arg) {
  if (!(arg instanceof api_pb.ListAuthorsRequest)) {
    throw new Error('Expected argument of type backend.ListAuthorsRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_backend_ListAuthorsRequest(buffer_arg) {
  return api_pb.ListAuthorsRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_backend_ListAuthorsResponse(arg) {
  if (!(arg instanceof api_pb.ListAuthorsResponse)) {
    throw new Error('Expected argument of type backend.ListAuthorsResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_backend_ListAuthorsResponse(buffer_arg) {
  return api_pb.ListAuthorsResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


var AuthorServiceService = exports.AuthorServiceService = {
  list: {
    path: '/backend.AuthorService/list',
    requestStream: false,
    responseStream: false,
    requestType: api_pb.ListAuthorsRequest,
    responseType: api_pb.ListAuthorsResponse,
    requestSerialize: serialize_backend_ListAuthorsRequest,
    requestDeserialize: deserialize_backend_ListAuthorsRequest,
    responseSerialize: serialize_backend_ListAuthorsResponse,
    responseDeserialize: deserialize_backend_ListAuthorsResponse,
  },
};

exports.AuthorServiceClient = grpc.makeGenericClientConstructor(AuthorServiceService);
