package org.cherrygods.main;

import listeners.EventClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author cherrygods
 * @since ２０１８－０６－１５　２３：１１：１３
 */
public class MainCoolDown extends JavaPlugin {
    public Map<UUID, Integer> cdTime = new HashMap<>();
    public Integer mastercd=10;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventClass(),this);
        loadConfig();
        run();
    }

    public void loadConfig() {
        //若为true则覆盖原config中的值
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (cdTime.isEmpty()) {
                    return;
                }
                for (UUID uuid : cdTime.keySet()) {
                    //获取当前的剩余冷却时间
                    int timeLeft = cdTime.get(uuid);
                    //判断剩余冷却时间是否小于或等于０
                    if (timeLeft <= 0) {
                        //冷却时间已过，可以将冷却时间给取消了
                        cdTime.remove(uuid);
                    }
                    //设置冷却时间-1
                    cdTime.put(uuid, timeLeft - 1);
                }
            }
        }.runTaskTimer(this, 0, 20L);
    }
}
