package fr.polarisdev.customstats.database;

import fr.polarisdev.customstats.utils.PlayerStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private final String url;
    private final String user;
    private final String password;

    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        // Create the player_stats table if it doesn't exist
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS player_stats (
                    uuid VARCHAR(36) PRIMARY KEY,
                    deaths INT DEFAULT 0,
                    blocks_mined INT DEFAULT 0,
                    kills INT DEFAULT 0,
                    entities_killed INT DEFAULT 0,
                    blocks_traveled INT DEFAULT 0,
                    blocks_flying INT DEFAULT 0,
                    sanctions INT DEFAULT 0,
                    current_grade VARCHAR(20) DEFAULT 'default'
                );
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void updatePlayerStats(PlayerStats playerStats) {
        String updateSQL = """
                INSERT INTO player_stats (uuid, deaths, blocks_mined, kills, entities_killed, blocks_traveled, blocks_flying, sanctions, current_grade)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    deaths = deaths + ?,
                    blocks_mined = blocks_mined + ?,
                    kills = kills + ?,
                    entities_killed = entities_killed + ?,
                    blocks_traveled = blocks_traveled + ?,
                    blocks_flying = blocks_flying + ?,
                    sanctions = sanctions + ?,
                    current_grade = ?;
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {

            // Set values for the insert
            statement.setString(1, playerStats.getPlayer().getUniqueId().toString());
            statement.setInt(2, playerStats.getDeaths());
            statement.setInt(3, playerStats.getBlocksMined());
            statement.setInt(4, playerStats.getKills());
            statement.setInt(5, playerStats.getEntitiesKilled());
            statement.setInt(6, playerStats.getBlocksTraveled());
            statement.setInt(7, playerStats.getBlocksFlying());
            statement.setInt(8, playerStats.getSanctions());
            statement.setString(9, playerStats.getCurrentGrade());

            // Set values for the ON DUPLICATE KEY clause
            statement.setInt(10, playerStats.getDeaths());
            statement.setInt(11, playerStats.getBlocksMined());
            statement.setInt(12, playerStats.getKills());
            statement.setInt(13, playerStats.getEntitiesKilled());
            statement.setInt(14, playerStats.getBlocksTraveled());
            statement.setInt(15, playerStats.getBlocksFlying());
            statement.setInt(16, playerStats.getSanctions());
            statement.setString(17, playerStats.getCurrentGrade());

            // Execute the update
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating player stats for UUID " + playerStats.getPlayer().getUniqueId() + ": " + e.getMessage());
        }
    }

    public PlayerStats getPlayerStats(String uuid) {
        PlayerStats playerStats = null;
        String selectSQL = "SELECT * FROM player_stats WHERE uuid = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a PlayerStats object from the result set
                    playerStats = new PlayerStats(
                            null, // Player object can be set later, as it may not be available here
                            resultSet.getInt("deaths"),
                            resultSet.getInt("blocks_mined"),
                            resultSet.getInt("kills"),
                            resultSet.getInt("entities_killed"),
                            resultSet.getInt("blocks_traveled"),
                            resultSet.getInt("blocks_flying"),
                            resultSet.getInt("sanctions"),
                            resultSet.getString("current_grade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving player stats for UUID " + uuid + ": " + e.getMessage());
        }

        return playerStats; // Return the PlayerStats object
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
