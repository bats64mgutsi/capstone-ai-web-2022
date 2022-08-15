import { IBackendClient, BackendClient } from "../core";

/**
 * Instantiates BackendClient to be able to make
 * rpc calls/requests to the backend
 * @returns An instance of IBackendClient
 */
export function createBackendClient(): IBackendClient {
    return new BackendClient();
}