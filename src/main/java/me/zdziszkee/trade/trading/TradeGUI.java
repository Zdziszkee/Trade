package me.zdziszkee.trade.trading;

import me.zdziszkee.trade.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TradeGUI implements GUI {
    private Main main;
    private Inventory inventory;
    private Player thisPlayer;
    private Player otherPlayer;
    private boolean isAccepted;
    private int numberOfOtherPlayerFreeSlots;
    private int numberOfItemsInTrade;
    private  boolean isClosed;
    private HashMap<Integer, ItemStack> myItems;


    public TradeGUI(Player thisPlayer, Player otherPlayer, Main main) {
        this.main = main;
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.inventory = Bukkit.createInventory(this, 36, "§7§lYou are trading with " + otherPlayer.getName());
        this.numberOfOtherPlayerFreeSlots = TradeUtils.getFreeSlots(otherPlayer);
        this.numberOfItemsInTrade = 0;
        this.isAccepted = false;
        this.isClosed = false;
        myItems = new HashMap<>();
    }

    @Override
    public void onGUIClick(Player whoClicked, int slot, Inventory clickedInventory, ItemStack clickedItem) {
        if(slot==30){
            this.isAccepted = true;
            updateInventories();
            if(getOtherPlayerGUI().isAccepted()){
                updateInventories();
                finalizeTrade();
            }
        }
        if(!this.isAccepted&&!getOtherPlayerGUI().isAccepted()){
        for(int i : TradeUtils.getActiveSlots()){
            if(i==slot){
                removeItem(i);
                return;
            }
        }}
    }


    @Override
    public void onGUIClose(Player player) {
        if(!(isAccepted && getOtherPlayerGUI().isAccepted())) {
            giveBackItems();
            if (!getOtherPlayerGUI().isClosed()) {
                this.isClosed = true;
                getOtherPlayerGUI().getThisPlayer().closeInventory();
            }
        }

    }

    @Override
    public void onGUIOpen(Player player) {

    }

    @Override
    public void onPlayerInventoryClick(Player player, int slot) {
    addItem(player.getInventory().getItem(slot),slot);

    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
    public void finalizeTrade(){
        for(int i:TradeUtils.getActiveSlots()){
            if(myItems.get(i)!=null&&myItems.get(i).getType()!=Material.AIR) {
                otherPlayer.getInventory().addItem(myItems.get(i));
            }
        }
        for(int i:TradeUtils.getActiveSlots()){
            if(getOtherPlayerGUI().getMyItems().get(i)!=null&&getOtherPlayerGUI().getMyItems().get(i).getType()!=Material.AIR) {
                thisPlayer.getInventory().addItem(getOtherPlayerGUI().getMyItems().get(i));
            }
        }
        thisPlayer.closeInventory();
        otherPlayer.closeInventory();
        main.getTradeHashMap().remove(thisPlayer.getName());
    }
    public TradeGUI getOtherPlayerGUI(){
        if(!main.getTradeHashMap().get(thisPlayer.getName()).getReceiverTradeGUI().equals(this)){
            return  main.getTradeHashMap().get(thisPlayer.getName()).getReceiverTradeGUI();
        }
        if(!main.getTradeHashMap().get(thisPlayer.getName()).getSenderTradeGUI().equals(this)){
            return main.getTradeHashMap().get(thisPlayer.getName()).getSenderTradeGUI();
        }
        return null;
    }

    public void addItem(ItemStack itemStack,int slot){
        if(!this.isAccepted&&!getOtherPlayerGUI().isAccepted()){
        for(int s :TradeUtils.getActiveSlots()){
            if(myItems.get(s)==null||myItems.get(s).getType()==Material.AIR){
                myItems.put(s,itemStack);
                updateInventories();
                thisPlayer.getInventory().setItem(slot,new ItemStack(Material.AIR));
                return;
            }
        }
        }

    }
    public void giveBackItems(){
        for(int i:TradeUtils.getActiveSlots()){
            if(myItems.get(i)!=null){
            thisPlayer.getInventory().addItem(myItems.get(i));
        }
        }
    }
    public void removeItem(int slot){
        ItemStack itemStack = myItems.get(slot);
        myItems.remove(slot);
        thisPlayer.getInventory().addItem(itemStack);
        updateInventories();
    }
    public void openInventory(){
        this.inventory.setContents(getContents());
        thisPlayer.openInventory(inventory);
    }
    public void updateInventory(){
        this.inventory.setContents(getContents());
    }
    public void updateInventories(){
        updateInventory();
        getOtherPlayerGUI().updateInventory();
    }

    public ItemStack[] getContents(){

        Inventory test = Bukkit.createInventory(null, 36);
        test.setItem(4, TradeUtils.getGlassPane(thisPlayer, otherPlayer));
        test.setItem(13, TradeUtils.getGlassPane(thisPlayer, otherPlayer));
        test.setItem(22,TradeUtils. getGlassPane(thisPlayer, otherPlayer));
        test.setItem(31, TradeUtils.getGlassPane(thisPlayer, otherPlayer));
        test.setItem(30, TradeUtils.getSenderButton(isAccepted));
        test.setItem(32, TradeUtils.getReceiverButton(getOtherPlayerGUI().isAccepted));
        for (int i : TradeUtils.getPassiveSlots()) {
            try {
                test.setItem(i, getOtherPlayerGUI().getMyItems().get(TradeUtils.convertToActiveSlot(i)));
            } catch (NullPointerException e) {
            }
        }
        for (int i : TradeUtils.getActiveSlots()) {
            try {
                test.setItem(i, myItems.get(i));
            } catch (NullPointerException e) {
            }
        }
        return test.getContents();

    }

    public Player getThisPlayer() {
        return thisPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public int getNumberOfOtherPlayerFreeSlots() {
        return numberOfOtherPlayerFreeSlots;
    }

    public int getNumberOfItemsInTrade() {
        return numberOfItemsInTrade;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public HashMap<Integer, ItemStack> getMyItems() {
        return myItems;
    }


}
