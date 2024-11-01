package fr.polarisdev.customstats.commands;

import fr.polarisdev.customstats.Main;
import fr.polarisdev.customstats.database.DatabaseManager;
import fr.polarisdev.customstats.runner.PlayerStatsUpdater;
import fr.polarisdev.customstats.utils.PlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsCommand implements CommandExecutor {

    private final DatabaseManager databaseManager;
    private final Main plugin;

    public StatsCommand(Main plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        PlayerStatsUpdater.startUpdating(plugin, databaseManager);

        String playerUUID = player.getUniqueId().toString();
        PlayerStats stats = databaseManager.getPlayerStats(playerUUID);

        player.sendMessage("§6§lStats for " + player.getName());
        player.sendMessage("§eDeaths: §f" + stats.getDeaths());
        player.sendMessage("§eBlocks mined: §f" + stats.getBlocksMined());
        player.sendMessage("§eKills: §f" + stats.getKills());
        player.sendMessage("§eEntities killed: §f" + stats.getEntitiesKilled());
        player.sendMessage("§eBlocks traveled: §f" + stats.getBlocksTraveled());
        player.sendMessage("§eBlocks flown: §f" + stats.getBlocksFlying());
        player.sendMessage("§eSanctions: §f" + stats.getSanctions());
        player.sendMessage("§eCurrent grade: §f" + stats.getCurrentGrade());

        return true;
    }
}
