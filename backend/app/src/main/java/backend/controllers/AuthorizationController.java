package backend.controllers;

import backend.Locator;
import backend.Tables.AdminsTable;
import backend.services.HashingService;

import java.sql.SQLException;

public class AuthorizationController {
    final AdminsTable adminsTable = (AdminsTable) Locator.instance.get(AdminsTable.class);
    final HashingService hashingService = (HashingService) Locator.instance.get(HashingService.class);

    // checks to see if the username and password provided is valid in the admin
    // table
    public boolean isAdmin(String username, String password) {
        try {
            adminsTable.getAdmin(username, hashingService.sha256(password));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
