package fr.polarisdev.customstats.api;

import com.google.gson.JsonObject;
import fi.iki.elonen.NanoHTTPD;
import fr.polarisdev.customstats.database.DatabaseManager;
import fr.polarisdev.customstats.utils.PlayerStats;

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

        PlayerStats stats = databaseManager.getPlayerStats(uuid);

        JsonObject statsJson = new JsonObject();
        statsJson.addProperty("uuid", uuid);
        statsJson.addProperty("deaths", stats.getDeaths());
        statsJson.addProperty("blocksMined", stats.getBlocksMined());
        statsJson.addProperty("kills", stats.getKills());
        statsJson.addProperty("entitiesKilled", stats.getEntitiesKilled());
        statsJson.addProperty("blocksTraveled", stats.getBlocksTraveled());
        statsJson.addProperty("blocksFlying", stats.getBlocksFlying());
        statsJson.addProperty("sanctions", stats.getSanctions());
        statsJson.addProperty("currentGrade", stats.getCurrentGrade());

        return newFixedLengthResponse(Response.Status.OK, "application/json", statsJson.toString());
    }
}