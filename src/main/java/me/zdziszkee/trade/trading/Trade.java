package me.zdziszkee.trade.trading;

import me.zdziszkee.trade.ZdziszkeeTrade;
import org.bukkit.entity.Player;

public class Trade {
    private final ZdziszkeeTrade zdziszkeeTrade;
    private final Player player1;
    private final Player player2;
    private final TradeGUI senderTradeGUI;
    private final TradeGUI receiverTradeGUI;

    public Trade(Player player1, Player player2, ZdziszkeeTrade zdziszkeeTrade) {
        this.player1 = player1;
        this.player2 = player2;
        this.zdziszkeeTrade = zdziszkeeTrade;
        senderTradeGUI = new TradeGUI(player1, player2, zdziszkeeTrade);
        receiverTradeGUI = new TradeGUI(player2, player1, zdziszkeeTrade);
        zdziszkeeTrade.getTradeHashMap().put(player1.getName(), this);
        zdziszkeeTrade.getTradeHashMap().put(player2.getName(), this);
    }

    public void openInventories() {
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
