package backend.http;

import backend.utils.UrlParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Object response = "";
        StringBuilder mimeType = new StringBuilder("application/json");
        int statusCode = 200;
        if(!exchange.getRequestMethod().equals("OPTIONS")) {
            try {
                System.out.println("Pre fetch");
                String requestBody = IOUtils.toString(exchange.getRequestBody());
                System.out.println("Post fetch");

                response = getResponse(UrlParser.parsePaths(exchange.getRequestURI().getPath()), exchange.getRequestHeaders(), requestBody, mimeType);
            } catch (Exception e) {
                response = e.toString();
                mimeType.replace(0, mimeType.length(), "text/text");
                System.err.println(e);
                e.printStackTrace();
                statusCode = 500;
            }
        }

        System.out.printf("On new request to %s %s\n", (CharSequence) exchange.getRequestMethod(), (CharSequence) exchange.getRequestURI().getPath());

        exchange.getResponseHeaders().add("Content-Type", mimeType.toString());
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        exchange.getResponseHeaders().add("Access-Control-Max-Age", "86400");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

        byte[] responseBytes = {};
        if(response instanceof byte[]) {
            responseBytes = (byte[]) response;
        } else if(response instanceof String) {
            responseBytes = ((String) response).getBytes();
        }
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        OutputStream out = exchange.getResponseBody();
        out.write(responseBytes);
        out.flush();
        out.close();
    }

    protected Object getResponse(String[] pathValues, Headers requestHeaders, String body) throws Exception{
        throw new UnsupportedOperationException();
    }
    protected Object getResponse(String[] pathValues, Headers requestHeaders, String body, StringBuilder outMimeType) throws Exception {
        return getResponse(pathValues, requestHeaders, body);
    }
}
