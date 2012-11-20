package com.jamesst20.jworldpermissions.utils;

import com.jamesst20.jworldpermissions.JWorldPermissions;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Methods {

    static JWorldPermissions plugin = JWorldPermissions.plugin;

    public static void regC(String command, CommandExecutor ce) {
        if (!plugin.getDescription().getCommands().toString().contains(command)) {
            log("The command " + command + " is not registred.");
            return;
        }
        Bukkit.getServer().getPluginCommand(command).setExecutor(ce);
    }

    /* This WILL override! */
    public static void writeConfigValues(String nodes, Object value) {
        plugin.getConfig().set(nodes, value);
        plugin.saveConfig();
    }

    /* This wont override anything! */
    public static void writeConfigDefaultValues(String nodes, Object value) {
        if (plugin.getConfig().get(nodes) == null) {
            plugin.getConfig().set(nodes, value);
            plugin.saveConfig();
        }
    }

    public static boolean isConsole(CommandSender cs) {
        return !(cs instanceof Player);
    }

    public static void log(String str) {
        Bukkit.getConsoleSender().sendMessage(str);
    }

    public static void sendPlayerMessage(Player p, String message) {
        p.sendMessage(prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC + message);
    }

    public static void sendPlayerMessage(CommandSender p, String message) {
        if (isConsole(p)) {
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC + message);
        } else {
            p.sendMessage(prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC + message);
        }
    }

    public static boolean hasPermission(Player p, String node) {
        if (p.hasPermission(node.toLowerCase()) || p.hasPermission(node)) {
            return true;
        }
        return false;
    }

    public static boolean hasPermission(CommandSender cs, String node) {
        if (isConsole(cs)) {
            return true;
        }
        return hasPermission((Player) cs, node);
    }

    public static boolean hasPermissionTell(CommandSender cs, String node) {
        if (isConsole(cs)) {
            return true;
        }
        if (!hasPermission((Player) cs, node)) {
            noPermissions(cs);
            return false;
        }
        return true;
    }

    public static void noPermissions(CommandSender cs) {
        if (isConsole(cs)) {
            log(prefix + "WTF, Console has no permission.");
        } else {
            cs.sendMessage(ChatColor.RED + "You do not have the permission to run this command.");
        }
    }

    public static void noPermissions(Player p) {
        p.sendMessage(ChatColor.RED + "You do not have the permission to run this command.");
    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + message);
    }

    public static String red(String word) {
        return ChatColor.RED + word + ChatColor.DARK_AQUA + "";
    }

    public static String green(String word) {
        return ChatColor.GREEN + word + ChatColor.DARK_AQUA + "";
    }

    public static String readFile(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Methods.log(ChatColor.RED + "Failed to create " + fileName);
            }
        }
        String text = "";
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            text = new String(chars);
            reader.close();
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to read file " + fileName + ".");
        }
        return text;
    }

    public static String getFormattedOnlinePlayers() {
        String players = "";
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            players += player.getDisplayName() + ", ";
        }
        players = replaceLast(players, ", ", "");
        return players;
    }

    public static String replaceLast(String string, String from, String to) {
        int lastIndex = string.lastIndexOf(from);
        if (lastIndex < 0) {
            return string;
        }
        String tail = string.substring(lastIndex).replaceFirst(from, to);
        return string.substring(0, lastIndex) + tail;
    }

    public static void playerNotFound(CommandSender cs, String player) {
        sendPlayerMessage(cs, "The player " + red(player) + " couldn't be found.");
    }

    public static String coloring(String textToTranslate) {
        if (textToTranslate == null) {
            return null;
        }
        char altColorChar = '&';
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    public static final String prefix = ChatColor.WHITE + "[" +ChatColor.GOLD + "JWorldPermissions" + ChatColor.WHITE + "] ";
}
