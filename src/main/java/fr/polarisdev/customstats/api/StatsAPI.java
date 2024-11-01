package fr.polarisdev.customstats.api;

import fi.iki.elonen.NanoHTTPD;
import fr.polarisdev.customstats.database.DatabaseManager;

public class StatsAPI extends NanoHTTPD {
    private final DatabaseManager databaseManager;

    public StatsAPI(int port, DatabaseManager databaseManager) {
        super(port);
        this.databaseManager = databaseManager;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uuid = session.getParms().get("uuid");
        if (uuid == null) {
            return newFixedLengthResponse(Response.Status.BAD_REQUEST, "application/json", "{\"error\": \"UUID is required\"}");
        }

        String playerStatsJson = databaseManager.getPlayerStats(uuid);
        return newFixedLengthResponse(Response.Status.OK, "application/json", playerStatsJson);
    }
}