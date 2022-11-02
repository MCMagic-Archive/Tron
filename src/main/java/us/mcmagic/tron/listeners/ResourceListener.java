package us.mcmagic.tron.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.mcmagic.mcmagiccore.player.User;
import us.mcmagic.mcmagiccore.resource.CurrentPackReceivedEvent;

/**
 * Created by Marc on 12/26/15
 */
public class ResourceListener implements Listener {

    @EventHandler
    public void onCurrentPackReceived(CurrentPackReceivedEvent event) {
        User user = event.getUser();
        Player player = Bukkit.getPlayer(user.getUniqueId());
        if (player == null) {
            return;
        }
        if (!event.getPacks().equalsIgnoreCase("tron")) {
            player.sendMessage(ChatColor.GREEN + "You must accept the Tron Resource Pack to play this game!");
        }
    }
}