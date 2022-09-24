package backend.controllers;

import backend.ApplicationModels.Stats;
import backend.Locator;
import backend.Tables.*;
import com.google.gson.Gson;

import java.sql.SQLException;

public class StatsController {
    final KeyValuesTable keyValuesTable = (KeyValuesTable) Locator.instance.get(KeyValuesTable.class);

    public Stats getStats() throws SQLException {
        final String statsJson = keyValuesTable.get(Stats.class.getName());
        return new Gson().fromJson(statsJson, Stats.class);
    }
}
