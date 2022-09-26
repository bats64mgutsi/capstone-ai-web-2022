import { IHttpClient } from "./types";

export class HttpClient implements IHttpClient {
  private onBeforeRequest?: BeforeRequestHook;
  private onAfterRequest?: AfterRequestHook;
  public baseUrl: string;

  constructor(config: HttpClientConfig = {}) {
    this.baseUrl = config.baseUrl || "";
    this.onBeforeRequest = config.onBeforeRequest;
    this.onAfterRequest = config.onAfterRequest;
  }

  setBaseUrl(baseUrl: string): void {
    this.baseUrl = baseUrl;
  }

  get<T>(path: string, headers: any = {}): Promise<T> {
    return this.request<T>(path, "GET", null, headers);
  }

  post<TBody, TResult>(
    path: string,
    body: TBody,
    headers: any = {}
  ): Promise<TResult> {
    return this.request<TResult>(path, "POST", body, headers);
  }

  put<TBody, TResult>(
    path: string,
    body: TBody,
    headers: any = {}
  ): Promise<TResult> {
    return this.request<TResult>(path, "PUT", body, headers);
  }

  patch<TBody, TResult>(
    path: string,
    body: TBody,
    headers: any = {}
  ): Promise<TResult> {
    return this.request<TResult>(path, "PATCH", body, headers);
  }

  delete<T>(path: string, headers: any = {}): Promise<T> {
    return this.request<T>(path, "DELETE", headers);
  }

  async request<T>(
    path: string,
    method = "GET",
    body: any = "",
    headers: any = {}
  ): Promise<T> {
    const config: RequestConfig = {
      method,
      mode: "cors",
      headers: new Headers({
        "Content-Type": "application/json",
        ...headers,
      }),
    };

    if (body) {
      config.body = JSON.stringify(body);
    }

    if (this.onBeforeRequest) {
      this.onBeforeRequest(config);
    }

    const url = `${this.baseUrl}${path}`;
    const resp = await fetch(url, config);
    const contentType = resp.headers.get("Content-Type");
    if(contentType !== "application/json") throw new Error(await resp.text());

    const json = resp.status !== 204 ? await resp.json() : undefined;

    if (this.onAfterRequest) {
      this.onAfterRequest(config, resp, json);
    }

    if (!isSuccessCode(resp.status)) {
      throw new ApiError(resp, json);
    }

    return json as T;
  }
}

class ApiError extends Error {
  public readonly statusCode: number;
  public readonly statusText: string;
  public readonly code: string;
  public readonly message: string;

  constructor(httpResp: Response, body: any) {
    super();
    this.statusCode = httpResp.status;
    this.statusText = httpResp.statusText;
    this.code = body.code as string;
    this.message = body.message as string;
  }
}

export function isSuccessResponse(resp: Response): boolean {
  return isSuccessCode(resp.status);
}

export function isSuccessCode(status: number): boolean {
  return status >= 200 && status < 400;
}

export type RequestBody =
  | BodyInit
  | null
  | undefined
  | ReadableStream<Uint8Array>;

export interface RequestConfig {
  method: string;
  mode?: RequestMode;
  headers: Headers;
  body?: RequestBody;
}

export interface HttpClientConfig {
  baseUrl?: string;
  onBeforeRequest?: BeforeRequestHook;
  onAfterRequest?: AfterRequestHook;
}

export interface BeforeRequestHook {
  (config: RequestConfig): void;
}

export interface AfterRequestHook {
  (requestConfig: RequestConfig, response: Response, json: unknown): void;
}