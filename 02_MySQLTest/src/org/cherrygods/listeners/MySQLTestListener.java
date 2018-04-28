package org.cherrygods.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cherrygods.main.MySQLTestClass;
import org.cherrygods.utils.DBHelp;

/**
 * @author CherrGods
 * @since 2018-4-27 22:35:01
 */
public class MySQLTestListener implements Listener {
    private MySQLTestClass plugin = MySQLTestClass.getPlugin(MySQLTestClass.class);
    private DBHelp db = new DBHelp();
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        
        System.out.printf(String.valueOf(event.getPlayer().getUniqueId()));
        db.playerExists(event.getPlayer().getUniqueId());
    }
}
