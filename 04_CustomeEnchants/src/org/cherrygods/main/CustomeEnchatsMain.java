package org.cherrygods.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CherryGods
 * @since  2018-4-30 23:20:10
 */
public class CustomeEnchatsMain extends JavaPlugin {
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!");
        super.onEnable();
    }
}
