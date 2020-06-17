package me.zdziszkee.trade.trading;

public class TradeRequest {
    private final String sender;
    private final String receiver;
    private long time;

    public TradeRequest(final String sender, final String receiver) {
        this.sender = sender;
        this.receiver = receiver;

    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
