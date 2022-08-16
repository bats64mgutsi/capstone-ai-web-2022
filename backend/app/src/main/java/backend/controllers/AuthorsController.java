package backend.controllers;
import backend.data.AuthorsTable;
import backend.models.Author;

import java.sql.SQLException;
import java.util.*;
abstract public class AuthorsController {
    public static List<Author> listAuthors() throws SQLException {
        return AuthorsTable.getInstance().listAuthors();
    }
}
