package org.cherrygods.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.cherrygods.listeners.CoolDownsListener;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author CherrGods
 * @since 2018-4-27 16:37:00
 */
public class CoolDownsClass extends JavaPlugin {
    //冷却时间
    public HashMap<UUID,Integer> cdTime = new HashMap<>();
    public int mastercd = 10;
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!\n");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CoolDownsListener(),this);
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!\n");
        loadConfig();
        runnableRunner();
        super.onEnable();
    }
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    public void runnableRunner(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(cdTime.isEmpty()){
                    return;
                }
                for (UUID uuid : cdTime.keySet()){
                    //剩余时间
                    int itemLeft = cdTime.get(uuid);
                    //如果冷却完毕就从hashMap中移除
                    if(itemLeft<=0){
                        cdTime.remove(uuid);
                    }else{
                        //否则就冷却时间-1
                        cdTime.put(uuid,itemLeft-1);
                    }
                }
            }
        }.runTaskTimer(this,0,20);
    }
}
