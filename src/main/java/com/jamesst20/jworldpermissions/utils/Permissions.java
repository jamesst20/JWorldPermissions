package com.jamesst20.jworldpermissions.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Permissions {

    public static boolean canBreakBlockID(Player p, Block block) {
        if (hasMultipleTypePerID(block.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.blocks.break." + block.getTypeId() + "." + block.getData());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.blocks.break." + block.getTypeId());
        }
    }

    public static boolean canPlaceBlockID(Player p, Block block) {
        if (hasMultipleTypePerID(block.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.blocks.place." + block.getTypeId() + "." + block.getData());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.blocks.place." + block.getTypeId());
        }
    }

    public static boolean canInteractWithBlockID(Player p, int ID) {
        return Methods.hasPermission(p, "JWorldPermissions.interact." + ID);
    }

    public static boolean canAttack(Player p, String entityName) {
        return Methods.hasPermission(p, "JWorldPermissions.attack." + entityName);
    }

    public static boolean canChat(Player p) {
        return Methods.hasPermission(p, "JWorldPermissions.chat");
    }

    public static boolean canHaveItem(Player p, ItemStack item) {
        if (hasMultipleTypePerID(item.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.item.have." + item.getTypeId() + "." + item.getDurability());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.item.have." + item.getTypeId());
        }
    }

    public static boolean canDropItem(Player p, ItemStack item) {
        if (hasMultipleTypePerID(item.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.item.drop." + item.getTypeId() + "." + item.getDurability());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.item.drop." + item.getTypeId());
        }
    }

    public static boolean canPickupItem(Player p, ItemStack item) {
        if (hasMultipleTypePerID(item.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.item.pickup." + item.getTypeId() + "." + item.getDurability());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.item.pickup." + item.getTypeId());
        }
    }

    public static boolean canUseItemID(Player p, ItemStack item) {
        if (hasMultipleTypePerID(item.getTypeId())){
            return Methods.hasPermission(p, "JWorldPermissions.item.use." + item.getTypeId() + "." + item.getDurability());
        }else{
            return Methods.hasPermission(p, "JWorldPermissions.item.use." + item.getTypeId());
        }        
    }

    public static boolean hasMultipleTypePerID(int itemID) {
        for (int i : IDS_WITH_MULTIPLE_TYPE) {
            if (i == itemID) {
                return true;
            }
        }
        return false;
    }

    public static boolean itemCanBeBroken(ItemStack item) {
        return item.getType().getMaxDurability() > 0;
    }
    private final static int[] IDS_WITH_MULTIPLE_TYPE = {5, 6, 17, 18, 24, 31, 35, 43, 44, 97, 98, 125, 126,
        139, 144, 263, 322, 351, 373, 383, 397};
}
