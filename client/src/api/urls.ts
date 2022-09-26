const getWebsocketUrl = (apiUrl: string) => {
    const urlObj = new URL(apiUrl);
    const wsProtocol = urlObj.protocol.replace("http", "ws");
    return `${wsProtocol}//${urlObj.host}`;
};

const origin = window.location.origin.replaceAll("8002", "8001").replaceAll("3000", "8002");
export const API_URL = import.meta.env.VITE_API_URL || origin;
export const WEBSOCKET_URL = getWebsocketUrl(API_URL);