package com.jamesst20.jworldpermissions.listener;

import com.jamesst20.jworldpermissions.locale.JLocale;
import com.jamesst20.jworldpermissions.utils.Methods;
import com.jamesst20.jworldpermissions.utils.Permissions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!Permissions.canChat(e.getPlayer())) {
            e.setCancelled(true);
            Methods.sendPlayerMessage(e.getPlayer(), JLocale.getMessage("Strings.NoChatPermissions"));
        }
    }

    @SuppressWarnings("all")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }
        if (e.getClickedBlock() != null) {
            /*Interact with objects such as workbench*/
            if (!Permissions.canInteractWithBlockID(e.getPlayer(), e.getClickedBlock().getTypeId())) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (isInteractable(e.getClickedBlock())) {
                        Methods.sendPlayerMessage(e.getPlayer(), formatInteractClickedItem(JLocale.getMessage("Strings.NoInteractPermissions"), e));
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            /*End Interact with objects*/
        }
        /*Item use.*/
        if (e.getItem() != null) {
            if (!Permissions.canUseItemID(e.getPlayer(), e.getItem())) {
                Methods.sendPlayerMessage(e.getPlayer(), formatInteractHoldingItem(JLocale.getMessage("Strings.NoUseItemPermissions"), e));
                e.setCancelled(true);
            }
        }
        /*End item use.*/
    }

    @EventHandler
    public void onEntityAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player playerWhoAttacked = ((Player) e.getDamager());
            if (e.getEntity() instanceof Player) {
                if (!Permissions.canAttack(playerWhoAttacked, "players")) {
                    Methods.sendPlayerMessage(playerWhoAttacked, formatOnEntityAttack(JLocale.getMessage("Strings.NoAttackPermissions"), "players"));
                    e.setCancelled(true);
                }
            } else {
                if (!Permissions.canAttack(playerWhoAttacked, e.getEntityType().name().toLowerCase())) {
                    Methods.sendPlayerMessage(playerWhoAttacked, formatOnEntityAttack(JLocale.getMessage("Strings.NoAttackPermissions"), e.getEntityType().name().toLowerCase()));
                    e.setCancelled(true);
                }
            }
        }
    }

    private String formatInteractClickedItem(String msg, PlayerInteractEvent e) {
        if (msg.contains("%item%")) {
            msg = msg.replaceAll("%item%", e.getClickedBlock().getType().name().toLowerCase());
        }
        return msg.replaceAll("_", " ");
    }

    private String formatInteractHoldingItem(String msg, PlayerInteractEvent e) {
        if (msg.contains("%item%")) {
            msg = msg.replaceAll("%item%", e.getMaterial().name().toLowerCase());
        }
        return msg.replaceAll("_", " ");
    }
    
    private String formatOnEntityAttack(String msg, String entityName) {
        if (msg.contains("%entity%")) {
            msg = msg.replaceAll("%entity%", entityName);
        }
        return msg;
    }

    private boolean isInteractable(Block block) {
        int[] interactableIds = {23, 25, 26, 54, 58, 61, 62, 64, 69, 70, 71, 84, 93, 94, 95, 96, 116, 117, 130, 131, 138, 143, 324, 328, 330, 333, 355, 356, 379};
        int blockID = block.getTypeId();
        for (int id : interactableIds) {
            if (blockID == id) {
                return true;
            }
        }
        return false;
    }
}
