package org.cherrygods.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cherrygods.main.CoolDown;

import java.util.UUID;

public class EventClass implements Listener {
    private CoolDown main = CoolDown.getPlugin(CoolDown.class);

    @EventHandler
    public void blockPlace(org.bukkit.event.block.BlockPlaceEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        //先判断玩家是否在冷却时间列表中
        if (!main.cdTime.containsKey(uuid)) {
            //判断玩家放置的是否是TNT
            if (block.getType().equals(Material.TNT)) {
                main.cdTime.put(uuid, main.initCD);
                player.sendMessage("你放置TNT的冷却时间重置了");
            }
        } else if (block.getType().equals(Material.TNT)) {
            e.setCancelled(true);
            player.sendMessage("你暂时不能放置TNT，冷却时间:" + main.cdTime.get(uuid) + "秒");
        }
        player.sendMessage("你放置了" + block.getType().name());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        //玩家进服时的冷却时间
        Integer pCDTime = main.getConfig().getInt(uuid + ".CoolDown_Left");
        player.sendMessage(String.valueOf(pCDTime));
        //判断玩家的冷却时间是否小于0
        if (pCDTime <= 0) {
            return;
        } else {
            main.cdTime.put(uuid, pCDTime);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        main.getConfig().set(uuid + ".CoolDown_Left", main.cdTime.get(uuid));
        main.saveConfig();
        main.cdTime.remove(uuid);
    }
}
