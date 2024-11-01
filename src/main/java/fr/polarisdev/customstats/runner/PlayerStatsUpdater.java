package fr.polarisdev.customstats.runner;

import fr.polarisdev.customstats.Main;
import fr.polarisdev.customstats.database.DatabaseManager;
import fr.polarisdev.customstats.utils.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatsUpdater implements Runnable {

    private final DatabaseManager databaseManager;

    public PlayerStatsUpdater(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void run() {
        // Loop through all online players and update their stats
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStats playerStats = PlayerStats.GetPlayerStats(player);
            // Update player stats in the database
            databaseManager.updatePlayerStats(playerStats);
        }
        System.out.println("Player stats updated successfully.");
    }

    public static void startUpdating(Main plugin, DatabaseManager databaseManager) {
        PlayerStatsUpdater updater = new PlayerStatsUpdater(databaseManager);
        // Schedule the task to run every 1200 ticks (1 minute)
        Bukkit.getScheduler().runTaskTimer(plugin, updater, 0L, 1200L);
    }
}
