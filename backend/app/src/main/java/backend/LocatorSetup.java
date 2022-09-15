package backend;

import backend.Tables.*;
import backend.controllers.AuthorizationController;
import backend.controllers.AuthorsController;
import backend.controllers.NrfListController;
import backend.http.AuthorizationHttpHandler;
import backend.http.AuthorsHttpHandler;
import backend.http.NrfListHttpHandler;
import backend.httpClient.HttpClient;
import backend.services.GoogleScholarService;
import backend.services.HashingService;
import backend.services.UniqueIdService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class LocatorSetup {
    public static void setupLocator() throws SQLException {
        final Connection sqlCon = DriverManager.getConnection("jdbc:mysql://localhost/aiweb", "root", "root");
        Locator.instance.registerSingleton(sqlCon);

        Locator.instance.registerSingleton(new AdminsTable());
        Locator.instance.registerSingleton(new AuthorsTable());
        Locator.instance.registerSingleton(new AuthorToSubfieldTable());
        Locator.instance.registerSingleton(new ContributionsTable());
        Locator.instance.registerSingleton(new PublicationsTable());
        Locator.instance.registerSingleton(new SubfieldsTable());

        Locator.instance.registerSingleton(new HttpClient());

        Locator.instance.registerSingleton(new GoogleScholarService());
        Locator.instance.registerSingleton(new HashingService());
        Locator.instance.registerSingleton(new UniqueIdService());

        Locator.instance.registerSingleton(new AuthorizationController());
        Locator.instance.registerSingleton(new AuthorsController());
        Locator.instance.registerSingleton(new NrfListController());
    }
}
