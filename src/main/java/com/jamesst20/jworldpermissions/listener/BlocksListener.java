package com.jamesst20.jworldpermissions.listener;

import com.jamesst20.jworldpermissions.locale.JLocale;
import com.jamesst20.jworldpermissions.utils.Methods;
import com.jamesst20.jworldpermissions.utils.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlocksListener implements Listener{
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if (!Permissions.canBreakBlockID(e.getPlayer(), e.getBlock())){            
            Methods.sendPlayerMessage(e.getPlayer(), formatString(JLocale.getMessage("Strings.NoBreakPermissions"), e));
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (!Permissions.canPlaceBlockID(e.getPlayer(), e.getBlock())){            
            Methods.sendPlayerMessage(e.getPlayer(), formatString(JLocale.getMessage("Strings.NoPlacePermissions"), e));
            e.setCancelled(true);
        }
    }
    
    private String formatString(String str, BlockBreakEvent e){
        if(str.contains(str)){
            str = str.replaceAll("%block%", e.getBlock().getType().name().toLowerCase()).replaceAll("_", " ");
        }
        if(str.contains("%world%")){
          str = str.replaceAll("%world%", e.getPlayer().getWorld().getName());
        }
        return str;
    }
    private String formatString(String str, BlockPlaceEvent e){
        if(str.contains(str)){
            str = str.replaceAll("%block%", e.getBlock().getType().name().toLowerCase().replaceAll("_", " "));
        }
        if(str.contains("%world%")){
          str = str.replaceAll("%world%", e.getPlayer().getWorld().getName());
        }
        return str;
    }
}
