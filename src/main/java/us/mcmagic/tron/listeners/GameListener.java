package us.mcmagic.tron.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import us.mcmagic.tron.Tron;

/**
 * Created by Marc on 12/26/15
 */
public class GameListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Tron.gameUtil.callChat(event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Tron.gameUtil.callInteract(event);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Tron.gameUtil.callEntityInteract(event);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Tron.gameUtil.callMove(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Tron.gameUtil.callDamage(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Tron.gameUtil.callDamage(event);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }
}