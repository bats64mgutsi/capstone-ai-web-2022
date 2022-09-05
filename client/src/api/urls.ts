const getWebsocketUrl = (apiUrl: string) => {
    const urlObj = new URL(apiUrl);
    const wsProtocol = urlObj.protocol.replace("http", "ws");
    return `${wsProtocol}//${urlObj.host}`;
};
  
export const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8001";
export const WEBSOCKET_URL = getWebsocketUrl(API_URL);