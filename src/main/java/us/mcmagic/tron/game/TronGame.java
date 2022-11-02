package us.mcmagic.tron.game;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Marc on 12/26/15
 */
public abstract class TronGame {

    public abstract void start();

    public abstract void end();

    public abstract void handle(AsyncPlayerChatEvent event);

    public abstract void handle(PlayerInteractEvent event);

    public abstract void handle(PlayerMoveEvent event);

    public abstract void handle(EntityDamageEvent event);

    public abstract void handle(EntityDamageByEntityEvent event);

    public abstract void handle(PlayerInteractEntityEvent event);
}