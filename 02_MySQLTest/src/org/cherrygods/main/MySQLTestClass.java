package org.cherrygods.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.cherrygods.listeners.MySQLTestListener;
import org.cherrygods.utils.DBHelp;

import java.sql.Connection;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CherrGods
 * @since 2018-4-27 18:30:13
 */
public class MySQLTestClass extends JavaPlugin {
    private Connection connection;
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public String host,database,username,password,table;
    public int port;
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Disable!\n");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        loadConfig();
        mysqlSetup();
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"has been Enable!\n");
        super.onEnable();
    }
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+getName()+" >> "
                +ChatColor.AQUA+"config has been loaded!\n");
    }
    public void mysqlSetup() {
        //从config.yml拿mysql服务器信息
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        table = this.getConfig().getString("table");

        //将当前线程强制锁住
        synchronized (this) {
            try {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(
                        DriverManager.getConnection("jdbc:mysql://"
                        +this.host+":"
                        +this.port+"/"
                        +this.database,this.username,this.password)
                );
                Bukkit.getConsoleSender().sendMessage("\n\n"+ChatColor.GREEN+this.getName()+" >> "
                        +ChatColor.AQUA+"MYSQL CONNECTED！");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
