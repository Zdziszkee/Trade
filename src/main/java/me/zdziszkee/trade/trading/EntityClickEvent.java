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
    private final ZdziszkeeTrade main;
    private final Map<String, Long> lastTrigger = new HashMap<>();

    public EntityClickEvent(final ZdziszkeeTrade main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityClick(final PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            if (e.getPlayer().isSneaking()) {
                final Player sender = e.getPlayer();
                final Player receiver = (Player) e.getRightClicked();
                final Long last = lastTrigger.get(e.getPlayer().getName());
                if (last != null && System.currentTimeMillis() < last + 1000L) return;
                lastTrigger.put(sender.getName(), System.currentTimeMillis());
                main.getTradeRequestHashMap().put(sender.getName(), new TradeRequest(sender.getName(), receiver.getName()));
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "You have sent trade request to " + receiver.getName());
                receiver.sendMessage(ChatColor.LIGHT_PURPLE + "You received trade request from " + receiver.getName());
                if (main.getTradeRequestHashMap().get(receiver.getName()) != null) {
                    final String senderReceiver = main.getTradeRequestHashMap().get(sender.getName()).getReceiver();
                    final String receiverReceiver = main.getTradeRequestHashMap().get(receiver.getName()).getReceiver();
                    if (senderReceiver.equals(receiver.getName()) && receiverReceiver.equals(sender.getName())) {
                        final Trade trade = new Trade(sender, receiver, main);
                        main.getTradeHashMap().put(sender.getName(), trade);
                        main.getTradeHashMap().put(receiver.getName(), trade);
                        trade.openInventories();

                    }
                }
            }
        }
    }
}
