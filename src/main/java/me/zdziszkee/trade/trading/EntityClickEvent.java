package me.zdziszkee.trade.trading;


import me.zdziszkee.trade.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityClickEvent implements Listener {
    private Main main;
    private Map<String, Long> lastTrigger = new HashMap<String, Long>();
    public EntityClickEvent(Main main){
        this.main = main;
    }
    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked() instanceof Player){
            if(e.getPlayer().isSneaking()){
                Player sender = e.getPlayer();
                Player receiver = (Player) e.getRightClicked();
                Long last = lastTrigger.get(e.getPlayer().getName());
                if (last != null && System.currentTimeMillis() < last + 1000L) return;
                lastTrigger.put(sender.getName(), System.currentTimeMillis());
                main.getTradeRequestHashMap().put(sender.getName(), new TradeRequest(sender.getName(), receiver.getName()));
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Wystales prosbe o trade do " + receiver.getName());
                receiver.sendMessage(ChatColor.LIGHT_PURPLE + "Otrzymales Prosbe o trade od " + receiver.getName());
                Bukkit.broadcastMessage("1");
                if (main.getTradeRequestHashMap().get(receiver.getName()) != null) {
                    Bukkit.broadcastMessage("2");
                    String senderReceiver = main.getTradeRequestHashMap().get(sender.getName()).getReceiver();
                    String receiverReceiver = main.getTradeRequestHashMap().get(receiver.getName()).getReceiver();
                    if (senderReceiver.equals(receiver.getName()) && receiverReceiver.equals(sender.getName())) {
                        Trade trade = new Trade(sender,receiver,main);
                        main.getTradeHashMap().put(sender.getName(),trade);
                        main.getTradeHashMap().put(receiver.getName(),trade);
                        Bukkit.broadcastMessage("Rozpoczynanie wymiany...");
                        trade.openInventories();

                    }
                }
            }
        }
    }
}
