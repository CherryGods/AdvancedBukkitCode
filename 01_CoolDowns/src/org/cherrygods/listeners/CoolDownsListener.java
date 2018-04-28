package org.cherrygods.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cherrygods.main.CoolDownsClass;

import java.util.UUID;

/**
 * @author CherrGods
 * @since 2018-4-27 17:13:18
 */
public class CoolDownsListener implements Listener {
    private CoolDownsClass plugin = CoolDownsClass.getPlugin(CoolDownsClass.class);
    /**
     * ����
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        //��config.yml�õ���ȴʱ��
        int cdTime = plugin.getConfig().getInt(uuid+".CoolDowns_Left");
        if (cdTime <= 0){
            return;
        }else{
            plugin.cdTime.put(uuid,cdTime);
        }
    }
    /**
     * �˷�
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        plugin.getConfig().set(uuid+".CoolDowns_Left",plugin.cdTime.get(uuid));
        plugin.saveConfig();
        plugin.cdTime.remove(uuid);
    }
    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        //�жϸ�����ҵ�uuid�ж��Ƿ�����ȴ�б���
        if(!plugin.cdTime.containsKey(uuid)){
            //�жϷ��õķ����Ƿ�Ϊ��ʯ��
            if(block.getType().equals(Material.DIAMOND_BLOCK)) {
                //����һ��10�����ȴʱ��
                plugin.cdTime.put(uuid,plugin.mastercd);
                player.sendMessage(ChatColor.YELLOW+plugin.getName()+" >> "+ChatColor.DARK_GREEN+"You have been added to the CD!");
            }
        }else{
            event.setCancelled(true);
            player.sendMessage(ChatColor.YELLOW+plugin.getName()+" >> "
                    +ChatColor.RED+"You still have "
                    +ChatColor.AQUA+plugin.cdTime.get(uuid)+" seconds "
                    +ChatColor.RED+"left still you can place "+ChatColor.YELLOW+block.getType());
        }
    }
}
