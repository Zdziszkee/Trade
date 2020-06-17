package me.zdziszkee.trade;

import me.zdziszkee.trade.trading.EntityClickEvent;
import me.zdziszkee.trade.trading.Trade;
import me.zdziszkee.trade.trading.TradeRequest;
import me.zdziszkee.trade.trading.tradegui.GUIListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class ZdziszkeeTrade extends JavaPlugin {
    /**
     * Hashmap storing all active trades
     */
    private final Map<String, Trade> tradeHashMap = new HashMap<>();
    /**
     * Hashmap storing last trade request of specified player
     */
    private final Map<String, TradeRequest> tradeRequestHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityClickEvent(this), this);

    }

    /**
     * Adding Trade class object to hashmap
     *
     * @param playerName the player which contest in Trade
     * @param trade      trade object
     */
    public void addTrade(final String playerName, final Trade trade) {
        tradeHashMap.put(playerName, trade);
    }

    /**
     * Method for removing trade object  from hashmap
     *
     * @param playerName the player which contest in Trade
     */
    public void removeTrade(final String playerName) {
        try {
            tradeHashMap.remove(playerName);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for getting the trade
     *
     * @param playerName the player which contest in trade
     * @return trade object
     */
    public Trade getTrade(final String playerName) {
        return tradeHashMap.get(playerName);
    }

    /**
     * Method for adding trade request to hashmap
     *
     * @param playerName   playerName which contest in trade
     * @param tradeRequest object
     */
    public void addTradeRequest(final String playerName, final TradeRequest tradeRequest) {
        tradeRequestHashMap.put(playerName, tradeRequest);
    }

    /**
     * Getting the trade request of given player
     *
     * @param playerName playerName of player u want get trade request
     * @return Trade request of player
     */
    public TradeRequest getTradeRequest(final String playerName) {
        return tradeRequestHashMap.get(playerName);
    }


    @Override
    public void onDisable() {
    }
}
