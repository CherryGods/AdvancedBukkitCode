package org.cherrygods.listeners;

import io.netty.handler.codec.http.HttpContentEncoder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cherrygods.main.MySQLTestClass;
import org.cherrygods.utils.DBHelp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author CherrGods
 * @since 2018-4-27 22:35:01
 */
public class MySQLTestListener implements Listener {
    private MySQLTestClass plugin = MySQLTestClass.getPlugin(MySQLTestClass.class);
    private DBHelp db = new DBHelp();
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        insertPlayer(player.getUniqueId(),player);
    }
    @EventHandler
    public void hit(PlayerInteractEvent event){
        updateMoney(event.getPlayer().getUniqueId());
        getMoney(event.getPlayer().getUniqueId());
    }

    /**
     * 查询玩家是否存在
   A  * @param uuid
     * @return
     */
    public boolean playerExists(UUID uuid){
        try {
            //sql语句
            PreparedStatement ps = plugin.getConnection()
                    .prepareStatement("SELECT * FROM "+plugin.table+" WHERE UUID=?");
            //把条件传进去
            ps.setString(1,uuid.toString());
            //结果集
            ResultSet rs = ps.executeQuery();
            //如果结果集里面有数据
            if(rs.next()){
                plugin.getServer().broadcastMessage(ChatColor.YELLOW+plugin.getName()+" >> "
                +ChatColor.AQUA+"PLAYER FOUND!");
                return true;
            }
            plugin.getServer().broadcastMessage(ChatColor.YELLOW+plugin.getName()+" >> "
                    +ChatColor.AQUA+"PLAYER NOT FOUND!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 新增一个玩家
     * @param UUID
     * @param player
     */
    public void insertPlayer(final UUID UUID,Player player){
        try {
            PreparedStatement ps = plugin.getConnection()
                    .prepareStatement("SELECT * FROM "+plugin.table+" WHERE UUID = ?");
            ps.setString(1,UUID.toString());
            ResultSet rs = ps.executeQuery();
            if(playerExists(UUID) != true){
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO "+plugin.table+" (UUID,NAME,MONEY) VALUES (?,?,?)");
                insert.setString(1,UUID.toString());
                insert.setString(2,player.getPlayer().getName());
                insert.setInt(3,500);
                insert.executeUpdate();
                plugin.getServer().broadcastMessage(ChatColor.YELLOW+plugin.getName()+" >> "
                        +ChatColor.AQUA+"PLAYER INSERTED!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据uuid修改money
     * @param uuid
     */
    public void updateMoney(UUID uuid){
        try {
            PreparedStatement ps = plugin.getConnection()
                    .prepareStatement("UPDATE "+plugin.table+" SET MONEY = ? WHERE UUID = ?");
            ps.setInt(1,1000);
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据uuid拿Money
     * @param uuid
     */
    public double getMoney(UUID uuid){
        try {
            PreparedStatement ps = plugin.getConnection()
                    .prepareStatement("SELECt * FROM "+plugin.table+" WHERE UUID = ?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            double money = rs.getInt("MONEY");
            return money;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.00;
    }
}
