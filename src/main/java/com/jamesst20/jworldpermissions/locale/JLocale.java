package com.jamesst20.jworldpermissions.locale;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jworldpermissions.JWorldPermissions;
import com.jamesst20.jworldpermissions.utils.Methods;
import java.io.File;

public class JLocale {
    public static JYamlConfiguration config;
    public static JWorldPermissions plugin = JWorldPermissions.plugin;
    
    public static void loadConfigs(){
        File locale = new File(plugin.getDataFolder(), plugin.getConfig().getString("translation.file"));
        if (locale.exists()){
            config = new JYamlConfiguration(plugin, plugin.getConfig().getString("translation.file"));
            setDefaultLocaleValues();
        }else{
            config = new JYamlConfiguration(plugin, "english.yml");
            setDefaultLocaleValues();
        }        
    }
    public static void reloadConfigs(){
        loadConfigs();
    }
    public static void setDefaultLocaleValues(){
        config.setDefault("Strings.NoPermissions", "&cYou have no permissions.");
        config.setDefault("Strings.NoBreakPermissions", "&cYou are not allowed to break %block%.");
        config.setDefault("Strings.NoPlacePermissions", "&cYou are not allowed to place %block%.");
        config.setDefault("Strings.NoChatPermissions", "&cYou are not allowed to chat.");
        config.setDefault("Strings.NoInteractPermissions", "&cYou are not allowed to interact with %item%.");
        config.setDefault("Strings.NoAttackPermissions", "&cYou are not allowed to attack %entity%.");        
        config.setDefault("Strings.NoHaveItemPermissions", "&cYou are not allowed to have %item%.");
        config.setDefault("Strings.NoDropPermissions", "&cYou are not allowed to drop %item%.");
        config.setDefault("Strings.NoUseItemPermissions", "&cYou are not allowed to use %item%.");
        config.saveConfig();
    }
    public static String getMessage(String nodes){
        return Methods.coloring(config.getConfig().getString(nodes, "&cYou don't have permissions."));
    }

}
