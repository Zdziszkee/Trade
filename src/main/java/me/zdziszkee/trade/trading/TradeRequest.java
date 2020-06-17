package me.zdziszkee.trade.trading;

public class TradeRequest {
    private String sender;
    private String receiver;
    private long time;

    public TradeRequest(String sender,String receiver) {
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
