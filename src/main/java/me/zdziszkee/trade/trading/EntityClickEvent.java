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

    /**
     * Logic for sending a trade request to player and accepting it
     *
     * @param e event
     */
    @EventHandler
    public void onEntityClick(final PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            if (e.getPlayer().isSneaking()) {
                final Long last = lastTrigger.get(e.getPlayer().getName());
                if (last != null && System.currentTimeMillis() < last + 1000L) return;
                final Player sender = e.getPlayer();
                final Player receiver = (Player) e.getRightClicked();
                lastTrigger.put(sender.getName(), System.currentTimeMillis());
                main.addTradeRequest(sender.getName(), new TradeRequest(sender.getName(), receiver.getName()));
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "You have sent trade request to " + receiver.getName());
                receiver.sendMessage(ChatColor.LIGHT_PURPLE + "You received trade request from " + receiver.getName());
                if (main.getTradeRequest(receiver.getName()) != null) {
                    final String senderReceiver = main.getTradeRequest(sender.getName()).getReceiver();
                    final String receiverReceiver = main.getTradeRequest(receiver.getName()).getReceiver();
                    if (senderReceiver.equals(receiver.getName()) && receiverReceiver.equals(sender.getName())) {
                        final Trade trade = new Trade(sender, receiver, main);
                        trade.openInventories();

                    }
                }
            }
        }
    }
}
