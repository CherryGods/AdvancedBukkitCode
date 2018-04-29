package org.cherrygods.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

/**
 * @author CherryGods
 * @since 2018-4-29 22:40:12
 */
public class RandomLootClass extends JavaPlugin implements Listener {
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        loadConfig();
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!");
        super.onEnable();
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        setupLoot(player);
    }
    public void setupLoot(Player player){
        List<String > configItems = this.getConfig().getStringList("Items");
        int index = new Random().nextInt(configItems.size());
        String items = configItems.get(index);

        ItemStack item = new ItemStack(Material.getMaterial(items.toUpperCase()));
        player.getInventory().addItem(item);
    }
}
