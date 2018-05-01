package org.cherrygods.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.cherrygods.listeners.CustomEnchatsListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author CherryGods
 * @since  2018-4-30 23:20:10
 */
public class CustomEnchatsMain extends JavaPlugin implements Listener {
    private CustomEnchatsListener ench = new CustomEnchatsListener(101);
    @Override
    public void onEnable() {
        LoadEnchantments();
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!");
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(ench,this);
        super.onEnable();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!");
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");
            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer,Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            HashMap<Integer,Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);
            if(byId.containsKey(ench.getId())){
                byId.remove(ench.getId());
            }
            if(byName.containsKey(ench.getName())){
                byName.remove(ench.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDisable();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+ench.getName()+" I");
        meta.setDisplayName(ChatColor.GOLD + "CherryGods Axe");
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }
    private void LoadEnchantments() {
        try {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null,true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }


            try {
                Enchantment.registerEnchantment(ench);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
