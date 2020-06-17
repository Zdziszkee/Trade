package me.zdziszkee.trade.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface GUI extends InventoryHolder {
     void onGUIClick(Player whoClicked, int slot, Inventory clickedInventory, ItemStack clickedItem);
     void onGUIClose(Player player);
     void onGUIOpen(Player player);
     void onPlayerInventoryClick(Player player, int slot);

}
