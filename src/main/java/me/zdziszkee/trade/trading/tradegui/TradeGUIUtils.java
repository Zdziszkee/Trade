package me.zdziszkee.trade.trading.tradegui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TradeGUIUtils {
    /**
     * Method for converting the slot on the left side of trade to slot on right side
     *
     * @param passiveSlot slot we want to convert
     * @return passive slot
     */
    public static int convertToActiveSlot(int passiveSlot) {
        final int[] activeSlots = getActiveSlots();
        final int[] passiveSlots = getPassiveSlots();
        for (int i = 0; i < passiveSlots.length; i++) {
            if (passiveSlots[i] == passiveSlot) {
                return activeSlots[i];
            }
        }
        return 0;
    }

    /**
     * Method for getting atctive slots array
     *
     * @return active slots array
     */
    public static int[] getActiveSlots() {
        return new int[]{
                0, 1, 2, 3,
                9, 10, 11, 12,
                18, 19, 20, 21,
                27, 28, 29};
    }

    /**
     * Method for getting passive slots array
     * (the slots of player u are trading with on the left side )
     *
     * @return array of passive slots
     */
    public static int[] getPassiveSlots() {
        return new int[]{
                5, 6, 7, 8,
                14, 15, 16, 17,
                23, 24, 25, 26,
                33, 34, 35};
    }

    /**
     * Method for getting Confirm button
     *
     * @param isLocked representing if player locked trade
     * @return button for GUI
     */
    public static ItemStack getConfirmButton(final boolean isLocked) {
        if (!isLocked) {
            final ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (short) 8);
            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Trade confirmed!");
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        } else {
            return getReadyButton();
        }
    }

    /**
     * Method for getting Confirmed button
     *
     * @return itemstack for button
     */
    public static ItemStack getReadyButton() {
        final ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (short) 10);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Confirm trade");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Getting glasspanes for trade gui
     *
     * @param player1 player contesting in trade
     * @param player2 player contesting in trade
     * @return glassPane for GUI
     */
    public static ItemStack getGlassPane(final Player player1, final Player player2) {
        final ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName("§8§l<--");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§8§l" + player1.getName());
        lore.add("§8§l" + "-->");
        lore.add("§8§l" + player2.getName());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}

