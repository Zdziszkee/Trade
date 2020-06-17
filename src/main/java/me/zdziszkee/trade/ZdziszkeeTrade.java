package me.zdziszkee.trade;

import me.zdziszkee.trade.trading.EntityClickEvent;
import me.zdziszkee.trade.trading.GUIListener;
import me.zdziszkee.trade.trading.Trade;
import me.zdziszkee.trade.trading.TradeRequest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class ZdziszkeeTrade extends JavaPlugin {
    private final Map<String, Trade> tradeHashMap = new HashMap<>();
    private final Map<String, TradeRequest> tradeRequestHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityClickEvent(this), this);

    }

    public Map<String, Trade> getTradeHashMap() {
        return tradeHashMap;
    }

    public Map<String, TradeRequest> getTradeRequestHashMap() {
        return tradeRequestHashMap;
    }

    @Override
    public void onDisable() {
    }
}
