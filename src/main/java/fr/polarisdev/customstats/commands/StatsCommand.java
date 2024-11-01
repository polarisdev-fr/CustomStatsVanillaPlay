package fr.polarisdev.customstats.commands;

import fr.polarisdev.customstats.database.DatabaseManager;
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

    public StatsCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        String playerUuid = player.getUniqueId().toString();
        String playerStats = databaseManager.getPlayerStats(playerUuid);

        player.sendMessage(playerStats);

        return true;
    }
}
