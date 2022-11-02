package us.mcmagic.tron.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import us.mcmagic.mcmagiccore.MCMagicCore;

/**
 * Created by Marc on 12/26/15
 */
public class Lobby extends TronGame {

    @Override
    public void start() {
    }

    @Override
    public void end() {
    }

    @Override
    public void handle(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        MCMagicCore.chatManager.chatMessage(event.getPlayer(), event.getMessage());
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        if (hand == null) {
            return;
        }
        if (hand.getType().equals(Material.BED)) {
            player.performCommand("hub");
        }
    }

    @Override
    public void handle(PlayerMoveEvent event) {
    }

    @Override
    public void handle(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handle(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handle(PlayerInteractEntityEvent event) {
        event.setCancelled(true);
    }
}
