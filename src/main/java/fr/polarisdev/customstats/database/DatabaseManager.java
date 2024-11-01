package fr.polarisdev.customstats.database;

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

        // Attempt to create the table upon instantiation
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_stats (" +
                "uuid VARCHAR(36) PRIMARY KEY, " +
                "deaths INT DEFAULT 0, " +
                "blocks_mined INT DEFAULT 0" +
                ");";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerStats(String uuid, int deaths, int blocksMined) {
        String insertSQL = "INSERT INTO player_stats (uuid, deaths, blocks_mined) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE deaths = ?, blocks_mined = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, uuid);
            statement.setInt(2, deaths);
            statement.setInt(3, blocksMined);
            statement.setInt(4, deaths);
            statement.setInt(5, blocksMined);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerStats(String uuid) {
        String json = "{}"; // Default value
        String selectSQL = "SELECT * FROM player_stats WHERE uuid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    json = String.format("{\"uuid\": \"%s\", \"deaths\": %d, \"blocks_mined\": %d}",
                            resultSet.getString("uuid"),
                            resultSet.getInt("deaths"),
                            resultSet.getInt("blocks_mined"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
