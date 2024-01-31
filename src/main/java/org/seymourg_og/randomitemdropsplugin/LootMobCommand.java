package org.seymourg_og.randomitemdropsplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LootMobCommand implements CommandExecutor {
    private final RandomItemDropsPlugin plugin;

    public LootMobCommand(RandomItemDropsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lootmob")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("restart")) {
                // Логика перезагрузки плагина
                plugin.reloadConfig();  // Пример: перезагрузка конфигурации плагина

                sender.sendMessage("Плагин успешно перезагружен.");
                return true;
            } else {
                sender.sendMessage("Используйте: /rid restart");
                return true;
            }
        }
        return false;
    }
}
