import { Author } from "../types";
import { IHttpClient, IBackendClient } from "./types";

export class BackendClient implements IBackendClient {
  private client: IHttpClient;

  constructor(httpClient: IHttpClient) {
    this.client = httpClient;
  }

  async getAuthors(): Promise<Author[]> {
    return await this.client.get<Author[]>(`/authors/`);
    // return [
    //   { 
    //     id: 1,
    //     name: "Aharonson",
    //     initials: "V",
    //     title: "Prof",
    //     institution: "University of the Witwatersrand",
    //     rating: "C",
    //   },
    //   { 
    //     id: 2,
    //     name: "Aldrich",
    //     initials: "C",
    //     title: "Prof",
    //     institution: "Stellenbosch University",
    //     rating: "C",
    //   },
    //   { 
    //     id: 3,
    //     name: "Atemkeng Teufack",
    //     initials: "M",
    //     title: "Dr",
    //     institution: "Rhodes University",
    //     rating: "C",
    //   },
    //   { 
    //     id: 4,
    //     name: "Bagula",
    //     initials: "BA",
    //     title: "Prof",
    //     institution: "University of the Western Cape",
    //     rating: "C",
    //   },
    //   { 
    //     id: 5,
    //     name: "Daramola",
    //     initials: "JO",
    //     title: "Prof",
    //     institution: "Cape Peninsula University of Technology",
    //     rating: "C",
    //   },
    // ]
  }
}
