<<<<<<< HEAD
import {
    IBackendClient,
    BackendClient,
    HttpClient,
    AfterRequestHook,
    isSuccessResponse,
    BeforeRequestHook,
  } from "../core";
import { getAccessToken, setAccessToken } from "../core/auth";
import { LoginResult } from "../core/types";

/**
 * Adds an authorization configuration,
 * consisting of the user's token,
 * in the request header
 * @param config Configuration values of an api request
 */
const injectAuthTokenHook: BeforeRequestHook = (config) => {
    // const token = getAccessToken();
    const token = 'YWRtaW46cm9vdA==';
    if (token) {
        config.headers.append("Authorization", `Basic ${token}`);
    }
};

/**
 * Sets the locally stored access token variable
 * to the token returned in the login api response
 */
const setAuthTokenHook: AfterRequestHook = (config, resp, json) => {
    if (isSuccessResponse(resp)) {
        const result = json as LoginResult;
        if (result?.token) {
        setAccessToken(result.token);
        }
    }
};

/**
 * Call method injectAuthTokenHook
 * before sending api request
 */
const onBeforeRequest: BeforeRequestHook = (config) => {
    injectAuthTokenHook(config);
};

/**
 * Call method setAuthTokenHook
 * if api response contains access token
 */
const onAfterRequest: AfterRequestHook = (config, resp, json) => {
    setAuthTokenHook(config, resp, json);
};

/**
 * Instantiates BackendClient to be able to make
 * api requests to the backend
 * @param baseUrl The url at which the backend can be reached
 * @returns An instance of IBackendClient
 */
export function createBackendClient(baseUrl: string): IBackendClient {
    const client = new BackendClient(
    new HttpClient({
        baseUrl,
        onBeforeRequest,
        onAfterRequest,
    })
    );

    return client;
}
=======
import {
    IBackendClient,
    BackendClient,
    HttpClient,
    AfterRequestHook,
    isSuccessResponse,
    BeforeRequestHook,
  } from "../core";
import { getAccessToken, setAccessToken } from "../core/auth";
import { LoginResult } from "../core/types";

/**
 * Adds an authorization configuration,
 * consisting of the user's token,
 * in the request header
 * @param config Configuration values of an api request
 */
const injectAuthTokenHook: BeforeRequestHook = (config) => {
    const token = getAccessToken();
    if (token) {
        config.headers.append("Authorization", `Bearer ${token}`);
    }
};

/**
 * Sets the locally stored access token variable
 * to the token returned in the login api response
 */
const setAuthTokenHook: AfterRequestHook = (config, resp, json) => {
    if (isSuccessResponse(resp)) {
        const result = json as LoginResult;
        if (result?.token) {
        setAccessToken(result.token);
        }
    }
};

/**
 * Call method injectAuthTokenHook
 * before sending api request
 */
const onBeforeRequest: BeforeRequestHook = (config) => {
    injectAuthTokenHook(config);
};

/**
 * Call method setAuthTokenHook
 * if api response contains access token
 */
const onAfterRequest: AfterRequestHook = (config, resp, json) => {
    setAuthTokenHook(config, resp, json);
};

/**
 * Instantiates BackendClient to be able to make
 * api requests to the backend
 * @param baseUrl The url at which the backend can be reached
 * @returns An instance of IBackendClient
 */
export function createBackendClient(baseUrl: string): IBackendClient {
    const client = new BackendClient(
    new HttpClient({
        baseUrl,
        onBeforeRequest,
        onAfterRequest,
    })
    );

    return client;
}
>>>>>>> master
    