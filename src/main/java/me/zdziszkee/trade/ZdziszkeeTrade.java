package me.zdziszkee.trade;

import me.zdziszkee.trade.configuration.Config;
import me.zdziszkee.trade.trading.EntityClickEvent;
import me.zdziszkee.trade.trading.Trade;
import me.zdziszkee.trade.trading.TradeRequest;
import me.zdziszkee.trade.trading.tradegui.GUIListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class ZdziszkeeTrade extends JavaPlugin {
    Config tradeLogs;
    /**
     * Hashmap storing all active trades
     */
    private final Map<Player, Trade> tradeHashMap = new HashMap<>();
    /**
     * Hashmap storing last trade request of specified player
     */
    private final Map<Player, TradeRequest> tradeRequestHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityClickEvent(this), this);
        this.tradeLogs = new Config(this, "tradeLogs.yml");
        this.tradeLogs.saveConfig();

    }


    public Config getTradeLogs() {
        return tradeLogs;
    }

    /**
     * Adding Trade class object to hashmap
     *
     * @param player the player which contest in Trade
     * @param trade  trade object
     */
    public void addTrade(final Player player, final Trade trade) {
        tradeHashMap.put(player, trade);
    }

    /**
     * Method for removing trade object  from hashmap
     *
     * @param player the player which contest in Trade
     */
    public void removeTrade(final Player player) {
        try {
            tradeHashMap.remove(player);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for getting the trade
     *
     * @param player the player which contest in trade
     * @return trade object
     */
    public Trade getTrade(final Player player) {
        return tradeHashMap.get(player);
    }

    /**
     * Method for adding trade request to hashmap
     *
     * @param player       playerName which contest in trade
     * @param tradeRequest object
     */
    public void addTradeRequest(final Player player, final TradeRequest tradeRequest) {
        tradeRequestHashMap.put(player, tradeRequest);
    }

    /**
     * Getting the trade request of given player
     *
     * @param player playerName of player u want get trade request
     * @return Trade request of player
     */
    public TradeRequest getTradeRequest(final Player player) {
        return tradeRequestHashMap.get(player);
    }

    public void removeTradeRequest(final Player player) {
        tradeRequestHashMap.remove(player);
    }


    @Override
    public void onDisable() {
    }
}
