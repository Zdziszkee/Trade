package me.zdziszkee.trade.trading;

import me.zdziszkee.trade.ZdziszkeeTrade;
import me.zdziszkee.trade.trading.tradegui.TradeGUI;
import org.bukkit.entity.Player;

public class Trade {
    /**
     * This class is representing trade between 2 players
     */
    private final TradeGUI senderTradeGUI;
    private final TradeGUI receiverTradeGUI;

    public Trade(Player player1, Player player2, ZdziszkeeTrade zdziszkeeTrade) {
        senderTradeGUI = new TradeGUI(player1, player2, zdziszkeeTrade);
        receiverTradeGUI = new TradeGUI(player2, player1, zdziszkeeTrade);
        zdziszkeeTrade.addTrade(player1.getName(), this);
        zdziszkeeTrade.addTrade(player2.getName(), this);
    }

    public void openInventories() {
        senderTradeGUI.openInventory();
        receiverTradeGUI.openInventory();
    }


    public TradeGUI getSenderTradeGUI() {
        return senderTradeGUI;
    }

    public TradeGUI getReceiverTradeGUI() {
        return receiverTradeGUI;
    }

}
