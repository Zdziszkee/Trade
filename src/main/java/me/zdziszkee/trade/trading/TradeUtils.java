package me.zdziszkee.trade.trading;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class TradeUtils {

    public static int convertToActiveSlot(int passiveSlot) {
        int[] activeSlots = getActiveSlots();
        int[] passiveSlots = getPassiveSlots();
        for (int i = 0; i < passiveSlots.length; i++) {
            if (passiveSlots[i] == passiveSlot) {
                return activeSlots[i];
            }
        }
        return 0;
    }
    public static int[] getActiveSlots() {
        return new int[]{
                0, 1, 2, 3,
                9, 10, 11, 12,
                18, 19, 20, 21,
                27, 28, 29};
    }

    public static int[] getPassiveSlots() {
        return new int[]{
                5, 6, 7, 8,
                14, 15, 16, 17,
                23, 24, 25, 26,
                33, 34, 35};
    }
    public static void removeItemFromTrade(Player player, ItemStack itemStack, Inventory inventory,int slot) {
        if(itemStack != null&& itemStack.getType()!= Material.AIR) {
            player.getInventory().addItem(itemStack);
            inventory.setItem(slot,new ItemStack(Material.AIR));
        }
        }
        public static void getDefaultContents(Inventory inventory,Player sender,Player receiver,boolean senderLocked, boolean receiverLocked){
            inventory.setItem(4, getGlassPane(receiver, sender));
            inventory.setItem(13, getGlassPane(receiver, sender));
            inventory.setItem(22, getGlassPane(receiver, sender));
            inventory.setItem(31, getGlassPane(receiver, sender));
            inventory.setItem(30, getReceiverButton(senderLocked));
            inventory.setItem(32, getSenderButton(receiverLocked));
    }
    public static ItemStack getSenderButton(boolean senderLocked) {
        if(!senderLocked){
            ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (short) 8);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Confirm trade");
            itemStack.setItemMeta(itemMeta);
            return itemStack;}else
        {
            return getReadyButton();
        }
    }
    public static ItemStack getReceiverButton(boolean receiverLocked) {
        if(!receiverLocked){
            ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (short) 8);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Confirm Trade");
            itemStack.setItemMeta(itemMeta);
            return itemStack;}else
        {
            return getReadyButton();
        }
    }
    public static ItemStack getReadyButton(){
        ItemStack itemStack = new ItemStack(Material.INK_SACK,1, (short) 10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN+"Potwiedziles gotowosc!");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getGlassPane(Player player1, Player player2) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName("§8§l<--");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§8§l" + player1.getName());
        lore.add("§8§l" + "-->");
        lore.add("§8§l" + player2.getName());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

   public static  int getFreeSlots(Player player){
       int counter = 0;
        try {
            for (int i =0;i<35;i++) {
                if (player.getInventory().getItem(i).getType() == Material.AIR) {
                    counter++;
                }
            }
        }catch (NullPointerException e){counter++;}
        return  counter;
   }
   public static int getFreeslot(Player player){
      return   player.getInventory().firstEmpty();
   }

}

