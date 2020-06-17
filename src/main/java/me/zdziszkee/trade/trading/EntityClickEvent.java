package me.zdziszkee.trade.trading;


import me.zdziszkee.trade.ZdziszkeeTrade;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityClickEvent implements Listener {
    private final ZdziszkeeTrade zdziszkeeTrade;
    private final Map<String, Long> lastTrigger = new HashMap<>();

    public EntityClickEvent(ZdziszkeeTrade zdziszkeeTrade) {
        this.zdziszkeeTrade = zdziszkeeTrade;
    }

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            if (e.getPlayer().isSneaking()) {
                final Player sender = e.getPlayer();
                final Player receiver = (Player) e.getRightClicked();
                final Long last = lastTrigger.get(e.getPlayer().getName());
                if (last != null && System.currentTimeMillis() < last + 1000L) return;
                lastTrigger.put(sender.getName(), System.currentTimeMillis());
                zdziszkeeTrade.getTradeRequestHashMap().put(sender.getName(), new TradeRequest(sender.getName(), receiver.getName()));
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "You have sent trade request to " + receiver.getName());
                receiver.sendMessage(ChatColor.LIGHT_PURPLE + "You have received trade request from " + receiver.getName());
                if (zdziszkeeTrade.getTradeRequestHashMap().get(receiver.getName()) != null) {
                    final String senderReceiver = zdziszkeeTrade.getTradeRequestHashMap().get(sender.getName()).getReceiver();
                    final String receiverReceiver = zdziszkeeTrade.getTradeRequestHashMap().get(receiver.getName()).getReceiver();
                    if (senderReceiver.equals(receiver.getName()) && receiverReceiver.equals(sender.getName())) {
                        final Trade trade = new Trade(sender, receiver, zdziszkeeTrade);
                        zdziszkeeTrade.getTradeHashMap().put(sender.getName(), trade);
                        zdziszkeeTrade.getTradeHashMap().put(receiver.getName(), trade);
                        trade.openInventories();

                    }
                }
            }
        }
    }
}
