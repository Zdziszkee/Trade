package me.zdziszkee.trade.trading.tradegui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class GUIListener implements Listener {
    /**
     * Events needed for GUI to work
     *
     * @param e event
     */
    @EventHandler
    public void onInvClick(final InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            e.setCancelled(true);
            final GUI gui = (GUI) e.getInventory().getHolder();
            gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getClickedInventory(), e.getCurrentItem());
        }

    }

    @EventHandler
    public void onPlayerInvClick(final InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof GUI) {
            if (e.getClickedInventory().getType() != null) {
                if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
                    e.setCancelled(true);
                    final GUI gui = (GUI) e.getInventory().getHolder();
                    gui.onPlayerInventoryClick((Player) e.getWhoClicked(), e.getSlot());
                }
            }
        }
    }

    @EventHandler
    public void onInvClose(final InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            final GUI gui = (GUI) e.getInventory().getHolder();
            gui.onGUIClose((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onInvOpen(final InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            final GUI gui = (GUI) e.getInventory().getHolder();
            gui.onGUIOpen((Player) e.getPlayer());
        }
    }
}
