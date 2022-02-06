package itsadamly.noctora.events;

import itsadamly.noctora.commands.playerinfo;
import itsadamly.noctora.sqlthing.sqltask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerinfoEvent implements Listener {

    sqltask data = new sqltask();

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        try
        {
            new playerinfo().infobox(player);
            new playerinfo().taskfunction();

        } catch (Exception error)
        {
            error.printStackTrace();
        }

        boolean hasNotified = data.hasInterestNotified(player.getUniqueId());

        if (!hasNotified) {
            player.sendMessage(ChatColor.GREEN + "It's a new month, you have received your monthly bank savings interest!");
            player.sendMessage(ChatColor.GREEN + "Your new bank balance : " + String.format("%.2f", data.getMoney(player.getUniqueId())));
            data.updateHasInterestNotified(player.getUniqueId(), "1");
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        new playerinfo().deletebox(player);
    }
}
