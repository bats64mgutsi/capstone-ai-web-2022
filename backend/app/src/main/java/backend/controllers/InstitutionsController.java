package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class InstitutionsController {
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);

    public List<Institution> listInstitutions() throws SQLException {
        return institutionsTable.listAll();
    }
}