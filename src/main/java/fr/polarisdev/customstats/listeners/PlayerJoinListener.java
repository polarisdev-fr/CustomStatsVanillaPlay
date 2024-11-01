package fr.polarisdev.customstats.listeners;

import fr.polarisdev.customstats.Main;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        int deaths = player.getStatistic(org.bukkit.Statistic.DEATHS);
        int blockMined = 0;
        for (org.bukkit.Material material : org.bukkit.Material.values()) {
            if (material.isBlock()) {
                blockMined += player.getStatistic(org.bukkit.Statistic.MINE_BLOCK, material);
            }
        }

        plugin.getDatabaseManager().updatePlayerStats(uuid, deaths, blockMined);
    }
}
