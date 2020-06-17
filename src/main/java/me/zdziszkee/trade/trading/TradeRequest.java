package me.zdziszkee.trade.trading;

import org.bukkit.entity.Player;

public class TradeRequest {
    /**
     * Class storing data about trade request
     */
    private final Player sender;
    private final Player receiver;
    private long time;

    public TradeRequest(final Player sender, final Player receiver) {
        this.sender = sender;
        this.receiver = receiver;

    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }
}
