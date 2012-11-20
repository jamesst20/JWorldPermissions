package com.jamesst20.jworldpermissions.listener;

import com.jamesst20.jworldpermissions.locale.JLocale;
import com.jamesst20.jworldpermissions.utils.Methods;
import com.jamesst20.jworldpermissions.utils.Permissions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onHeldItemChange(PlayerItemHeldEvent e) {
        if (e.getPlayer().getInventory().getItem(e.getNewSlot()) != null) {
            if (!Permissions.canHaveItem(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()))) {
                Player player = e.getPlayer();
                ItemStack item = player.getInventory().getItem(e.getNewSlot());
                if (item != null && item.getType() != Material.AIR) {
                    Methods.sendPlayerMessage(player, formatHeldItemChange(JLocale.getMessage("Strings.NoHaveItemPermissions"), e));
                    player.getInventory().setItem(e.getNewSlot(), new ItemStack(Material.AIR));
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!Permissions.canDropItem(e.getPlayer(), e.getItemDrop().getItemStack())) {
            if (e.getItemDrop().getItemStack() != null) {
                Methods.sendPlayerMessage(e.getPlayer(), formatDropItem(JLocale.getMessage("Strings.NoDropPermissions"), e));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (!Permissions.canPickupItem(e.getPlayer(), e.getItem().getItemStack())) {
            if (e.getItem().getItemStack() != null) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player player = (Player) e.getPlayer();
            if (player.getItemInHand().getType() != Material.AIR) {
                if (!Permissions.canHaveItem(player, player.getItemInHand())) {
                    Methods.sendPlayerMessage(player, formatInventoryClose(JLocale.getMessage("Strings.NoHaveItemPermissions"), e));
                    player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                }
            }
        }
    }

    private String formatHeldItemChange(String str, PlayerItemHeldEvent e) {
        if (str.contains("%item%")) {
            str = str.replaceAll("%item%", e.getPlayer().getInventory().getItem(e.getNewSlot()).getType().name().toLowerCase()).replaceAll("_", " ");
        }
        return str;
    }

    private String formatDropItem(String str, PlayerDropItemEvent e) {
        if (str.contains("%item%")) {
            str = str.replaceAll("%item%", e.getItemDrop().getItemStack().getType().name().toLowerCase()).replaceAll("_", " ");
        }
        return str;
    }

    private String formatInventoryClose(String str, InventoryCloseEvent e) {
        if (str.contains("%item%")) {
            str = str.replaceAll("%item%", e.getPlayer().getItemInHand().getType().name().toLowerCase()).replaceAll("_", " ");
        }
        return str;
    }
}
