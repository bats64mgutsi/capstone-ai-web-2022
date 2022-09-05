import { IBackendClient } from "../core";
import { createBackendClient } from "./backend";
import { API_URL } from "./urls";

let _backendClient: IBackendClient;

/**
 * Initializes the Backend API client used
 * throughout the app, the client can be retrieved
 * by calling `backendClient()`.
 * @param client The client to use. This should only
 * be passed during testing to mock the client.
 * By default a client will be created based on the configured
 * API_URL.
 * @returns The initialized backend client.
 */
export function initBackendClient(client?: IBackendClient): IBackendClient {
  _backendClient = client || createBackendClient(API_URL);
  return _backendClient;
}

/**
 * Gets the Backend API client
 * @returns The Backend API client
 */
export function backendClient(): IBackendClient {
  if (!_backendClient) {
    throw new Error(
      "Backend Client not initialize. Please initialize with initBackendClient() when starting up the app."
    );
  }

  return _backendClient;
}