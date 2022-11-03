package me.candiesjar.fallbackserver.listeners;

import me.candiesjar.fallbackserver.FallbackServerBungee;
import me.candiesjar.fallbackserver.enums.BungeeConfig;
import me.candiesjar.fallbackserver.enums.BungeeMessages;
import me.candiesjar.fallbackserver.objects.Placeholder;
import me.candiesjar.fallbackserver.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler(priority = 64)
    public void onChat(ChatEvent event) {

        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        if (!event.isProxyCommand()) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String playerServer = player.getServer().getInfo().getName();
        String message = event.getMessage();

        if (player.hasPermission(BungeeConfig.ADMIN_PERMISSION.getString())) {
            return;
        }

        if (message.length() > 1) {
            String[] args = message.split(" ");
            message = args[0];
        }

        boolean checkMessage = Utils.checkMessage(message, playerServer);

        if (checkMessage) {
            event.setCancelled(true);
            BungeeMessages.BLOCKED_COMMAND.send(player, new Placeholder("prefix", FallbackServerBungee.getInstance().getPrefix()));
        }
    }
}
