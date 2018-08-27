package org.cherrygods.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.cherrygods.listeners.EventClass;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author CherryGods
 * @since 23点07分
 */
public class CoolDown extends JavaPlugin{
    //存放剩余cd时间
    public HashMap<UUID,Integer> cdTime = new HashMap<>();
    //初始化cd时间
    public Integer initCD = 10;
    @Override
    public void onDisable() {
        getLogger().info("物品冷却插件已关闭。");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        registerCommandOrListener();
        loadConfig();
        setCDTimeRunnable();
        getLogger().info("物品冷却插件已开启。");
        super.onEnable();
    }

    /**
     * 加载配置文件
     */
    void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    /**
     * 注册命令或监听器
     */
    void registerCommandOrListener(){
        getServer().getPluginManager().registerEvents(new EventClass(), this);
    }

    /**
     * 多任务,进行处理冷却时间
     */
    void setCDTimeRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {
                if (cdTime.isEmpty())
                    return;
                //遍历uuid
                for (UUID uuid:cdTime.keySet()){
                    //获得uuid对应的剩余冷却时间
                    int timeLeft = cdTime.get(uuid);
                    //如果冷却时间小于等于0就从冷却时间的列表中移除
                    if (timeLeft<=0){
                        cdTime.remove(uuid);
                    }else{
                        //否则将冷却时间-1
                        cdTime.put(uuid,timeLeft-1);
                    }
                }
            }
        }.runTaskTimer(this,0,20);
    }
}
