package me.zdziszkee.trade;

import me.zdziszkee.trade.trading.EntityClickEvent;
import me.zdziszkee.trade.trading.GUIListener;
import me.zdziszkee.trade.trading.Trade;
import me.zdziszkee.trade.trading.TradeRequest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {
    private HashMap<String, Trade> tradeHashMap;
    private HashMap<String, TradeRequest> tradeRequestHashMap;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new EntityClickEvent(this),this);
        tradeHashMap = new HashMap<String, Trade>();
        tradeRequestHashMap = new HashMap<String, TradeRequest>();
    }

    public HashMap<String, Trade> getTradeHashMap() {
        return tradeHashMap;
    }

    public HashMap<String, TradeRequest> getTradeRequestHashMap() {
        return tradeRequestHashMap;
    }

    @Override
    public void onDisable() {
    }
}
