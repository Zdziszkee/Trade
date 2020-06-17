package me.zdziszkee.trade.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface GUI extends InventoryHolder {
     void onGUIClick(final Player whoClicked, final int slot, final Inventory clickedInventory, final ItemStack clickedItem);

     void onGUIClose(final Player player);

     void onGUIOpen(final Player player);

     void onPlayerInventoryClick(final Player player, final int slot);

}
