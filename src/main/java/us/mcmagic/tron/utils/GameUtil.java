package us.mcmagic.tron.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import us.mcmagic.mcmagiccore.arcade.GameCountdownEvent;
import us.mcmagic.mcmagiccore.arcade.GameStartEvent;
import us.mcmagic.tron.Tron;
import us.mcmagic.tron.clock.GameClock;
import us.mcmagic.tron.clock.actions.MusicAction;
import us.mcmagic.tron.clock.actions.TitleAction;
import us.mcmagic.tron.game.*;
import us.mcmagic.tron.handlers.AudioFile;
import us.mcmagic.tron.handlers.GameState;

/**
 * Created by Marc on 12/26/15
 */
public class GameUtil implements Listener {
    public GameClock clock = new GameClock();
    private LaserTag laserTag = new LaserTag();
    private DiscWars discWars = new DiscWars();
    private LightCycles lightCycles = new LightCycles();
    private Lobby lobby = new Lobby();

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        LobbyCountdownUtil.clearSpace();
        laserTag.start();
        Bukkit.getScheduler().runTaskTimer(Tron.getInstance(), clock, 0L, 1L);
    }

    @EventHandler
    public void onGameCountdown(GameCountdownEvent event) {
        int num = event.getNumber();
        if (num <= 5 && num >= 1) {
            LobbyCountdownUtil.countdownNumber(num);
        }
    }

    public void loadActions(GameState state) {
        switch (state) {
            case SERVER_STARTING:
                break;
            case IN_LOBBY:
                break;
            case LASERTAG:
                clock.addAction(new TitleAction(0, ChatColor.AQUA + "Laser Tag", ChatColor.GOLD +
                        "Shoot as many players as possible!", 20, 60, 20));
                clock.addAction(new MusicAction(0, AudioFile.LASER.getFileName()));
                break;
            case DISCWARS:
                break;
            case LIGHTCYCLES:
                break;
            case POSTGAME:
                break;
        }
    }

    public void callInteract(PlayerInteractEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public void callMove(PlayerMoveEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public void callDamage(EntityDamageEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public void callDamage(EntityDamageByEntityEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public LaserTag getLaserTag() {
        return laserTag;
    }

    public void callEntityInteract(PlayerInteractEntityEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public void callChat(AsyncPlayerChatEvent event) {
        switch (GameState.getState()) {
            case IN_LOBBY:
            case POSTGAME:
                lobby.handle(event);
                break;
            case LASERTAG:
                laserTag.handle(event);
                break;
            case DISCWARS:
                discWars.handle(event);
                break;
            case LIGHTCYCLES:
                lightCycles.handle(event);
                break;
        }
    }

    public TronGame getCurrentGame() {
        switch (GameState.getState()) {
            case LASERTAG:
                return laserTag;
            case DISCWARS:
                return discWars;
            case LIGHTCYCLES:
                return lightCycles;
        }
        return null;
    }

    public DiscWars getDiscWars() {
        return discWars;
    }
}