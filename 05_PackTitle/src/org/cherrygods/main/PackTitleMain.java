package org.cherrygods.main;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Cherrygods
 * @since 2018-5-1 22:13:47
 */
public class PackTitleMain extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"§aWelcome "+event.getPlayer().getName()+"\"}"),100,20,20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
    }
}
