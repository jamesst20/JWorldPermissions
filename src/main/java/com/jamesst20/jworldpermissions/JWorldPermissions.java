package com.jamesst20.jworldpermissions;

import com.jamesst20.jworldpermissions.listener.BlocksListener;
import com.jamesst20.jworldpermissions.listener.InventoryListener;
import com.jamesst20.jworldpermissions.listener.PlayerListener;
import com.jamesst20.jworldpermissions.locale.JLocale;
import com.jamesst20.jworldpermissions.mcstats.Metrics;
import com.jamesst20.jworldpermissions.utils.Methods;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class JWorldPermissions extends JavaPlugin {

    public static JWorldPermissions plugin;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to send MCStats!");
        }
        writeDefaultSettings();
        registerEvents();
        registerCommands();
        Methods.log("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        saveConfig();
        Methods.log("Disabled successfully!");
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new BlocksListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
    }

    private void writeDefaultSettings() {
        Methods.writeConfigDefaultValues("translation.file", "english.yml");
        JLocale.loadConfigs();
    }

    private void registerCommands() {
    }
}