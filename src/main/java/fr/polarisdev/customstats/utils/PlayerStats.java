package fr.polarisdev.customstats.utils;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import fr.polarisdev.customstats.utils.Utils;

public class PlayerStats {

    public Player player;

    public int deaths;
    public int blocksMined;
    public int kills;
    public int entitiesKilled;
    public int blocksTraveled;
    public int blocksFlying;
    public int sanctions;
    public String currentGrade;

    public PlayerStats(Player player, int deaths, int blocksMined, int kills, int entitiesKilled, int blocksTraveled, int blocksFlying, int sanctions, String currentGrade) {
        this.player = player;
        this.deaths = deaths;
        this.blocksMined = blocksMined;
        this.kills = kills;
        this.entitiesKilled = entitiesKilled;
        this.blocksTraveled = blocksTraveled;
        this.blocksFlying = blocksFlying;
        this.sanctions = sanctions;
        this.currentGrade = currentGrade;
    }

    public static PlayerStats GetPlayerStats(Player player) {

        return new PlayerStats(
                player,
                player.getStatistic(Statistic.DEATHS),
                Utils.GetMinedBlock(player),
                player.getStatistic(Statistic.PLAYER_KILLS),
                player.getStatistic(Statistic.MOB_KILLS),
                player.getStatistic(Statistic.WALK_ONE_CM),
                player.getStatistic(Statistic.FLY_ONE_CM),
                0,
                "default"
        );
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getBlocksMined() {
        return blocksMined;
    }

    public void setBlocksMined(int blocksMined) {
        this.blocksMined = blocksMined;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getEntitiesKilled() {
        return entitiesKilled;
    }

    public void setEntitiesKilled(int entitiesKilled) {
        this.entitiesKilled = entitiesKilled;
    }

    public int getBlocksTraveled() {
        return blocksTraveled;
    }

    public void setBlocksTraveled(int blocksTraveled) {
        this.blocksTraveled = blocksTraveled;
    }

    public int getBlocksFlying() {
        return blocksFlying;
    }

    public void setBlocksFlying(int blocksFlying) {
        this.blocksFlying = blocksFlying;
    }

    public int getSanctions() {
        return sanctions;
    }

    public void setSanctions(int sanctions) {
        this.sanctions = sanctions;
    }

    public String getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
    }
}
