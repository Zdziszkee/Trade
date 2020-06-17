package me.zdziszkee.trade.trading;

import me.zdziszkee.trade.Main;
import org.bukkit.entity.Player;

public class Trade {
    private Main main;
    private Player player1;
    private Player player2;
    private TradeGUI senderTradeGUI;
    private TradeGUI receiverTradeGUI;
    public Trade(Player player1,Player player2,Main main){
        this.player1= player1;
        this.player2 = player2;
        this.main = main;
        senderTradeGUI = new TradeGUI(player1,player2,main);
        receiverTradeGUI = new TradeGUI(player2,player1,main);
        main.getTradeHashMap().put(player1.getName(),this);
        main.getTradeHashMap().put(player2.getName(),this);
    }
    public void openInventories(){
        senderTradeGUI.openInventory();
        receiverTradeGUI.openInventory();
    }
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public TradeGUI getSenderTradeGUI() {
        return senderTradeGUI;
    }

    public TradeGUI getReceiverTradeGUI() {
        return receiverTradeGUI;
    }

}
