package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;
import java.sql.SQLException;
import java.util.*;

public class AiKeywordsController {
    final AiKeywordsTable aiKeywordsTable = (AiKeywordsTable) Locator.instance.get(AiKeywordsTable.class);

public List<String> listAikeywords() throws SQLException {
    return aiKeywordsTable.listAll();
}
}
