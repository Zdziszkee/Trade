package me.zdziszkee.trade.trading.tradegui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface GUI extends InventoryHolder {

    default void onGUIClick(final Player whoClicked, final int slot, final Inventory clickedInventory, final ItemStack clickedItem) {
    }

    default void onGUIClose(final Player player) {
    }

    default void onGUIOpen(final Player player) {
    }

    default void onPlayerInventoryClick(final Player player, final int slot) {
    }

}
