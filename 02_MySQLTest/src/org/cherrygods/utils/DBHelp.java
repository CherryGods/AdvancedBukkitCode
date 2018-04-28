package org.cherrygods.utils;

import java.sql.*;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.cherrygods.main.MySQLTestClass;

/**
 * @author CherrGods
 * @since 2018-4-27 20:52:41
 */
public class DBHelp{
    private MySQLTestClass plugin = MySQLTestClass.getPlugin(MySQLTestClass.class);
    private Connection connection;
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public String host,username,password,table,database;
    public int port=3306;
    public void mysqlSetup(){
        host = plugin.getConfig().getString("host");
        port = plugin.getConfig().getInt("port");
        database = plugin.getConfig().getString("database");
        username = plugin.getConfig().getString("username");
        password = plugin.getConfig().getString("password");
        table = plugin.getConfig().getString("table");
        try {
            synchronized (plugin){
                if (getConnection()!=null&&!getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(
                       DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                                        this.username, this.password));
                plugin.getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+plugin.getName()+" >> "+ChatColor.AQUA+"MYSQL CONNECTED!\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean playerExists(UUID uuid){
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM "+this.table+" WHERE UUID=?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                plugin.getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+plugin.getName()+" >> "
                        +ChatColor.AQUA+"Player Found!\n");
                return true;
            }
            plugin.getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+plugin.getName()+" >> "
                    +ChatColor.DARK_AQUA+"Player NOT Found!\n");
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
