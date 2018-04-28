package org.cherrygods.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.cherrygods.listeners.MySQLTestListener;
import org.cherrygods.utils.DBHelp;

/**
 * @author CherrGods
 * @since 2018-4-27 18:30:13
 */
public class MySQLTestClass extends JavaPlugin {
    private DBHelp dbHelp;
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!\n");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        dbHelp = new DBHelp();
        dbHelp.mysqlSetup();
        getServer().getPluginManager().registerEvents(new MySQLTestListener(),this);
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!\n");
        loadConfig();
        super.onEnable();
    }
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
