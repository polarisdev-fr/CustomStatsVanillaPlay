package fr.polarisdev.customstats.listeners;

import fr.polarisdev.customstats.Main;
import fr.polarisdev.customstats.runner.PlayerStatsUpdater;
import fr.polarisdev.customstats.utils.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerJoinListener implements Listener {

    private final Main plugin;

    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerStats playerStats = PlayerStats.GetPlayerStats(player);

        // Update player stats in the database
        plugin.getDatabaseManager().updatePlayerStats(playerStats);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerStats playerStats = PlayerStats.GetPlayerStats(player);

        // Update player stats in the database
        plugin.getDatabaseManager().updatePlayerStats(playerStats);
    }

}
