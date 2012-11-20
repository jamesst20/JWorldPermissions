package com.jamesst20.jworldpermissions.locale;

import com.jamesst20.jcommandessentials.Utils.ConfigMethods;
import com.jamesst20.jworldpermissions.JWorldPermissions;
import com.jamesst20.jworldpermissions.utils.Methods;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class JLocale {
    public static YamlConfiguration config = null;
    public static File configFile = null;
    public static JWorldPermissions plugin = JWorldPermissions.plugin;
    
    public static void loadConfigs(){
        File locale = new File(plugin.getDataFolder(), plugin.getConfig().getString("translation.file"));
        if (locale.exists()){
            configFile = locale;
            config = ConfigMethods.getCustomConfig(plugin.getConfig().getString("translation.file"));
            setDefaultLocaleValues();
        }else{
            configFile = ConfigMethods.getConfigFile("english.yml");
            config = ConfigMethods.getCustomConfig("english.yml");
            setDefaultLocaleValues();
        }        
    }
    public static void reloadConfigs(){
        loadConfigs();
    }
    public static void setDefaultLocaleValues(){
        ConfigMethods.setDefaultConfig(config, "Strings.NoPermissions", "&cYou have no permissions.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoBreakPermissions", "&cYou are not allowed to break %block%.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoPlacePermissions", "&cYou are not allowed to place %block%.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoChatPermissions", "&cYou are not allowed to chat.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoInteractPermissions", "&cYou are not allowed to interact with %item%.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoAttackPermissions", "&cYou are not allowed to attack %entity%.");        
        ConfigMethods.setDefaultConfig(config, "Strings.NoHaveItemPermissions", "&cYou are not allowed to have %item%.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoDropPermissions", "&cYou are not allowed to drop %item%.");
        ConfigMethods.setDefaultConfig(config, "Strings.NoUseItemPermissions", "&cYou are not allowed to use %item%.");
        ConfigMethods.saveConfig(configFile, config);
    }
    public static String getMessage(String nodes){
        return Methods.coloring(config.getString(nodes, "&cYou don't have permissions."));
    }

}
