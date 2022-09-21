package backend.controllers;

import backend.Locator;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class AiKeywordsController {
    final AiKeywordsTable aiKeywordsTable = (AiKeywordsTable) Locator.instance.get(AiKeywordsTable.class);

    public List<String> listAiKeywords() throws SQLException {
        return aiKeywordsTable.listAll();
    }

    public void setAiKeywords(List<String> aiKeywords) throws SQLException {
        aiKeywordsTable.clearAll();
        for(final String keyword : aiKeywords) {
            aiKeywordsTable.insertKeyword(keyword);
        }
    }
}
