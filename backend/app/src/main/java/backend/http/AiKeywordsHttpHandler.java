package backend.http;

import backend.Locator;
import backend.controllers.AiKeywordsController;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.nio.file.InvalidPathException;
import java.util.LinkedList;
import java.util.List;

public class AiKeywordsHttpHandler extends BaseHttpHandler {
    final AiKeywordsController aiKeywordsController = (AiKeywordsController) Locator.instance.get(AiKeywordsController.class);
    
    @Override
    public String getResponseAsString(String[] pathValues, Headers requestHeaders, String requestBody) throws Exception {
        if(pathValues[1].equals("aiKeywords")) {
            final List<String> aiKeywords = new LinkedList<>();
            aiKeywords.addAll(aiKeywordsController.listAikeywords());
            return new Gson().toJson(aiKeywords);
        } 
         else {
            throw new InvalidPathException(pathValues[1], "No such path in AiKeywordsHttpHandler", -1);
        }
    }
    
}
