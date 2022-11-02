package us.mcmagic.tron.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.potion.PotionEffect;
import us.mcmagic.tron.Tron;
import us.mcmagic.tron.handlers.GameState;

/**
 * Created by Marc on 12/26/15
 */
public class PlayerJoinAndLeave implements Listener {
    private Location lobby = new Location(Bukkit.getWorlds().get(0), 1.5, 68, 2.5, 180, 0);

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        switch (GameState.getState()) {
            case SERVER_STARTING:
                event.setKickMessage(ChatColor.AQUA + "This game is starting up!");
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                break;
            case IN_LOBBY:
                if (Tron.getPlayerDataMap().size() >= 16) {
                    event.setKickMessage(ChatColor.AQUA + "This game is full, try another one!");
                    event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                }
                return;
            case LASERTAG:
            case DISCWARS:
            case LIGHTCYCLES:
            case POSTGAME:
                event.setKickMessage(ChatColor.AQUA + "This game has already started, try another one!");
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                break;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Tron.createPlayerData(player.getUniqueId());
        player.teleport(lobby);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setExp(0.0F);
    }
}