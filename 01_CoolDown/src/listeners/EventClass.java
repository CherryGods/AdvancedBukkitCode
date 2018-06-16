package listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.cherrygods.main.MainCoolDown;

import java.util.UUID;

public class EventClass implements Listener {
    private MainCoolDown p = MainCoolDown.getPlugin(MainCoolDown.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        int cdTime = p.getConfig().getInt(uuid + ".CoolDown_Left");
        if (cdTime <= 0) {
            return;
        }
        p.cdTime.put(uuid, cdTime);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        p.getConfig().set(uuid+"Cooldown_Left",p.cdTime.get(uuid));
        p.cdTime.remove(uuid);
    }
    public void blockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!p.cdTime.containsKey(uuid)) {
            if (block.getType().equals(Material.DIAMOND_BLOCK)) {
                p.cdTime.put(uuid, p.mastercd);
                player.sendMessage(ChatColor.GREEN + "You have been added to the cooldown!");
            }
        } else {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You still have " + ChatColor.YELLOW + p.cdTime.get(uuid)
                    + " seconds " + ChatColor.RED + "left till you can place Diamond Blocks!");
        }
    }
}
