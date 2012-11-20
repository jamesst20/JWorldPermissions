package com.jamesst20.jcommandessentials.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.jamesst20.jworldpermissions.JWorldPermissions;
import com.jamesst20.jworldpermissions.utils.Methods;

public class ConfigMethods {

    public static JWorldPermissions plugin = JWorldPermissions.plugin;

    public static YamlConfiguration getCustomConfig(String file) {
        File customConfigFile = new File(plugin.getDataFolder(), file);
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                Methods.log(ChatColor.RED + "Failed to create " + file);
            }
        }
        return YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public static YamlConfiguration getCustomConfig(File customConfigFile) {
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                Methods.log(ChatColor.RED + "Failed to create " + customConfigFile.getName());
            }
        }
        return YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public static File getConfigFile(String filePath) {
        return new File(plugin.getDataFolder(), filePath);
    }

    public static void saveConfig(String file, YamlConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder(), file));
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to save config " + file);
        }
    }

    public static void saveConfig(File file, YamlConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to save config " + file);
        }
    }

    public static void dirCreate(String dir) {
        File file = new File(plugin.getDataFolder(), dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    public static void setDefaultConfig(YamlConfiguration config, String nodes, Object value){
        if (config.get(nodes) == null){
            config.set(nodes, value);
        }
    }
}
