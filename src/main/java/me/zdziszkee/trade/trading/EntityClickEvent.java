package me.zdziszkee.trade.trading;


import me.zdziszkee.trade.ZdziszkeeTrade;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
                receiver.playSound(receiver.getLocation(), Sound.BURP, 3, 3);
                lastTrigger.put(sender.getName(), System.currentTimeMillis());
                main.addTradeRequest(sender, new TradeRequest(sender, receiver));
                sender.sendMessage(ChatColor.GRAY + "You have sent trade request to " + ChatColor.DARK_GRAY + receiver.getName());
                receiver.sendMessage(ChatColor.GRAY + "You have received trade request from" + ChatColor.DARK_GRAY + receiver.getName());
                if (main.getTradeRequest(receiver) != null) {
                    final Player senderReceiver = main.getTradeRequest(sender).getReceiver();
                    final Player receiverReceiver = main.getTradeRequest(receiver).getReceiver();
                    if (senderReceiver.equals(receiver) && receiverReceiver.equals(sender)) {
                        final Trade trade = new Trade(sender, receiver, main);
                        trade.openInventories();

                    }
                }
            }
        }
    }
}
