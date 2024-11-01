package fr.polarisdev.customstats.utils;

import org.bukkit.entity.Player;

public class Utils {

    public static int GetMinedBlock(Player player) {
        int blockMined = 0;
        for (org.bukkit.Material material : org.bukkit.Material.values()) {
            if (material.isBlock()) {
                blockMined += player.getStatistic(org.bukkit.Statistic.MINE_BLOCK, material);
            }
        }
        return blockMined;
    }
}
