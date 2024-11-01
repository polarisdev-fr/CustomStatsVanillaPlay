package fr.polarisdev.customstats.listeners;

import fr.polarisdev.customstats.Main;
import fr.polarisdev.customstats.runner.PlayerStatsUpdater;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    private final Main plugin;

    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerStatsUpdater.startUpdating(plugin, plugin.getDatabaseManager());
    }

}
