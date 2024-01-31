package org.seymourg_og.randomitemdropsplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomItemDropsPlugin extends JavaPlugin {
    private LootMob lootMob;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        lootMob = new LootMob(this);
        lootMob.initializeItemsAndChances();
        getServer().getPluginManager().registerEvents(lootMob, this);
        getCommand("rid").setExecutor(new LootMobCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Дополнительная обработка команд, если необходимо
        return false;
    }

}
