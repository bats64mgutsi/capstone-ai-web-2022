import grpc from "@grpc/grpc-js";
import { AuthorServiceClient } from "./api_grpc_pb";
import { ListAuthorsRequest } from "./api_pb";

const client = new AuthorServiceClient(
  `localhost:443`,
  grpc.credentials.createInsecure()
);

// Call the list method on the server
const req: ListAuthorsRequest = {} as any;
client.list(req, (err, response) => {
  console.log("Server says: ");
  response.getAuthorsList().forEach((author, index, _) => {
    console.log(author.getName());
  });
});
