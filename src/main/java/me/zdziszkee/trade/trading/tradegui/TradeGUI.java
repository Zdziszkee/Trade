package me.zdziszkee.trade.trading.tradegui;

import me.zdziszkee.trade.ZdziszkeeTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TradeGUI implements GUI {
    private final ZdziszkeeTrade main;
    private final Inventory inventory;
    private final Player thisPlayer;
    private final Player otherPlayer;
    private final HashMap<Integer, ItemStack> myItems = new HashMap<>();
    private boolean isAccepted;
    private boolean isClosed;


    public TradeGUI(final Player thisPlayer, final Player otherPlayer, final ZdziszkeeTrade main) {
        this.main = main;
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.inventory = Bukkit.createInventory(this, 36, "§7§lTrading with " + otherPlayer.getName());
        this.isAccepted = false;
        this.isClosed = false;
    }

    @Override
    public void onGUIClick(final Player whoClicked, final int slot, final Inventory clickedInventory, final ItemStack clickedItem) {
        // Handling trade confirm button, and trade finalization

        if (slot == 30) {
            this.isAccepted = true;
            updateInventories();
            if (getOtherPlayerGUI().isAccepted()) {
                updateInventories();
                finalizeTrade();
            }
        }

        // Logic to allow player remove his items from trade
        // when trade is not accepted by any of players

        if (!this.isAccepted && !getOtherPlayerGUI().isAccepted()) {
            for (int i : TradeGUIUtils.getActiveSlots()) {
                if (i == slot) {
                    removeItem(i);
                    return;
                }
            }
        }
    }


    @Override
    public void onGUIClose(final Player player) {

        // Logic which synchronize player trade gui closing and
        //  giving back their items

        if (!(isAccepted && getOtherPlayerGUI().isAccepted())) {
            this.isClosed = true;
            giveBackItems();
            if (!getOtherPlayerGUI().isClosed()) {
                getOtherPlayerGUI().getThisPlayer().closeInventory();
            }
        }

    }

    @Override
    public void onGUIOpen(final Player player) {

    }

    @Override
    public void onPlayerInventoryClick(final Player player, final int slot) {

        // Logic for adding items to trade when
        // any of player haven't accepted trade

        addItem(player.getInventory().getItem(slot), slot);

    }

    /**
     * Method inherited from InventoryHolder interface
     *
     * @return inventory
     */
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Method for finalizing trade when both players accepted trade
     * It gives items to specified players
     */
    public void finalizeTrade() {
        for (int i : TradeGUIUtils.getActiveSlots()) {
            if (myItems.get(i) != null && myItems.get(i).getType() != Material.AIR) {
                otherPlayer.getInventory().addItem(myItems.get(i));
            }
        }
        for (int i : TradeGUIUtils.getActiveSlots()) {
            if (getOtherPlayerGUI().getMyItems().get(i) != null && getOtherPlayerGUI().getMyItems().get(i).getType() != Material.AIR) {
                thisPlayer.getInventory().addItem(getOtherPlayerGUI().getMyItems().get(i));
            }
        }
        thisPlayer.playSound(thisPlayer.getLocation(), Sound.ANVIL_BREAK, 3, 3);
        otherPlayer.playSound(otherPlayer.getLocation(), Sound.ANVIL_BREAK, 3, 3);
        thisPlayer.closeInventory();
        otherPlayer.closeInventory();
        main.removeTrade(thisPlayer);
    }

    /**
     * Getting the TradeGUI which player you are trading with view
     *
     * @return TradeGui object of player u are trading with
     */
    public TradeGUI getOtherPlayerGUI() {
        if (!main.getTrade(thisPlayer).getReceiverTradeGUI().equals(this)) {
            return main.getTrade(thisPlayer).getReceiverTradeGUI();
        }
        if (!main.getTrade(thisPlayer).getSenderTradeGUI().equals(this)) {
            return main.getTrade(thisPlayer).getSenderTradeGUI();
        }
        return null;
    }

    /**
     * Adding itemStack to trade
     *
     * @param itemStack the item u want to add to trade
     * @param slot      the slot in player inventory the item were
     */
    public void addItem(final ItemStack itemStack, final int slot) {
        if (!this.isAccepted && !getOtherPlayerGUI().isAccepted()) {
            for (int s : TradeGUIUtils.getActiveSlots()) {
                if (myItems.get(s) == null || myItems.get(s).getType() == Material.AIR) {
                    myItems.put(s, itemStack);
                    updateInventories();
                    thisPlayer.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    return;
                }
            }
        }

    }

    /**
     * Give back items which player put in trade
     */
    public void giveBackItems() {
        for (int i : TradeGUIUtils.getActiveSlots()) {
            if (myItems.get(i) != null) {
                thisPlayer.getInventory().addItem(myItems.get(i));
            }
        }
    }

    /**
     * Method for removing  itemStack from trade
     */
    public void removeItem(final int slot) {
        final ItemStack itemStack = myItems.get(slot);
        if (itemStack != null) {
            myItems.remove(slot);
            thisPlayer.getInventory().addItem(itemStack);
            updateInventories();
        }
    }

    /**
     * Opening the trade gui for player
     */
    public void openInventory() {
        this.inventory.setContents(getContents());
        thisPlayer.openInventory(inventory);
    }

    /**
     * Updating contents of TradeGui
     */
    public void updateInventory() {
        this.inventory.setContents(getContents());
    }

    /**
     * Updating both players TradeGUIS
     */
    public void updateInventories() {
        updateInventory();
        getOtherPlayerGUI().updateInventory();
    }

    /**
     * Getting contents for TradeGUI
     *
     * @return contents for TradeGUI
     */
    public ItemStack[] getContents() {

        ItemStack glassPane = TradeGUIUtils.getGlassPane(thisPlayer, otherPlayer);
        //Creating local gui for getting contents
        final Inventory test = Bukkit.createInventory(null, 36);
        test.setItem(4, glassPane);
        test.setItem(13, glassPane);
        test.setItem(22, glassPane);
        test.setItem(31, glassPane);
        test.setItem(30, TradeGUIUtils.getConfirmButton(isAccepted));
        test.setItem(32, TradeGUIUtils.getConfirmButton(getOtherPlayerGUI().isAccepted));
        //Getting the items player u are trading with put in trade
        for (int i : TradeGUIUtils.getPassiveSlots()) {
            if (getOtherPlayerGUI().getMyItems().get(TradeGUIUtils.convertToActiveSlot(i)) != null) {
                test.setItem(i, getOtherPlayerGUI().getMyItems().get(TradeGUIUtils.convertToActiveSlot(i)));
            }
        }
        //Getting the items you put in trade
        for (int i : TradeGUIUtils.getActiveSlots()) {
            if (myItems.get(i) != null) {
                test.setItem(i, myItems.get(i));
            }
        }
        return test.getContents();

    }

    public Player getThisPlayer() {
        return thisPlayer;
    }


    public boolean isAccepted() {
        return isAccepted;
    }


    public boolean isClosed() {
        return isClosed;
    }

    public HashMap<Integer, ItemStack> getMyItems() {
        return myItems;
    }


}
