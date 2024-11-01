package fr.polarisdev.customstats;

import fr.polarisdev.customstats.api.StatsAPI;
import fr.polarisdev.customstats.commands.StatsCommand;
import fr.polarisdev.customstats.database.DatabaseManager;
import fr.polarisdev.customstats.runner.PlayerStatsUpdater;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    private DatabaseManager databaseManager;
    private StatsAPI statsAPI;

    @Override
    public void onEnable() {
        // Sauvegarder la configuration par défaut si elle n'existe pas
        saveDefaultConfig();

        // Charger les paramètres de configuration
        String dbUrl = getConfig().getString("database.url");
        String dbUser = getConfig().getString("database.user");
        String dbPassword = getConfig().getString("database.password");
        int apiPort = getConfig().getInt("api.port");

        // Initialisez la base de données
        databaseManager = new DatabaseManager(dbUrl, dbUser, dbPassword);

        // Initialisez l'API
        statsAPI = new StatsAPI(apiPort, databaseManager);
        try {
            statsAPI.start();
            getLogger().info("StatsAPI started on port " + apiPort + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PluginCommand command = this.getCommand("customstats"); // Remplacez par le nom de votre commande
        if (command != null) {
            command.setExecutor(new StatsCommand(this, databaseManager)); // Assurez-vous que CustomStatsCommand implémente CommandExecutor
        } else {
            getLogger().severe("La commande 'customstats' n'est pas définie dans plugin.yml !");
        }

        // Enregistrez le listener
        PlayerStatsUpdater.startUpdating(this, databaseManager);
        getLogger().info("CustomStatsPlugin enabled.");
    }

    @Override
    public void onDisable() {
        try {
            statsAPI.stop();
            getLogger().info("StatsAPI stopped.");

            databaseManager.getConnection().close();
            getLogger().info("Database connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().info("CustomStatsPlugin disabled.");
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
