package us.mcmagic.tron;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import us.mcmagic.mcmagiccore.MCMagicCore;
import us.mcmagic.mcmagiccore.arcade.ServerState;
import us.mcmagic.tron.commands.Commandhub;
import us.mcmagic.tron.commands.Commandstartgame;
import us.mcmagic.tron.handlers.GameState;
import us.mcmagic.tron.handlers.PlayerData;
import us.mcmagic.tron.listeners.GameListener;
import us.mcmagic.tron.listeners.PlayerJoinAndLeave;
import us.mcmagic.tron.listeners.ResourceListener;
import us.mcmagic.tron.utils.GameUtil;
import us.mcmagic.tron.utils.LobbyCountdownUtil;

import java.util.HashMap;
import java.util.UUID;

public class Tron extends JavaPlugin {
    private static Tron instance;
    public static GameUtil gameUtil;
    private static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        gameUtil = new GameUtil();
        registerListeners();
        registerCommands();
        String c = ChatColor.GOLD.toString();
        String s = "          ";
        MCMagicCore.gameManager.setGameData("Tron", ChatColor.GREEN + s + "                Laser Tag",
                new String[]{"     Derezz as many players on the other team",
                        "       before time runs out!"}, 1, 16, 5);
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                LobbyCountdownUtil.clearSpace();
                GameState.setState(GameState.IN_LOBBY);
                MCMagicCore.gameManager.setState(MCMagicCore.getMCMagicConfig().serverName, ServerState.ONLINE);
            }
        }, 20L);
    }

    @Override
    public void onDisable() {
        MCMagicCore.gameManager.setState(MCMagicCore.getMCMagicConfig().serverName, ServerState.RESTARTING);
        MCMagicCore.gameManager.setPlayerCount(MCMagicCore.getMCMagicConfig().serverName, 0);
    }

    public static Tron getInstance() {
        return instance;
    }

    public static PlayerData createPlayerData(UUID uuid) {
        PlayerData data = new PlayerData(uuid);
        playerData.put(uuid, data);
        return data;
    }

    public static PlayerData removePlayerData(UUID uuid) {
        return playerData.remove(uuid);
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return playerData.get(uuid);
    }

    public static HashMap<UUID, PlayerData> getPlayerDataMap() {
        return new HashMap<>(playerData);
    }

    private void registerCommands() {
        getCommand("hub").setExecutor(new Commandhub());
        getCommand("startgame").setExecutor(new Commandstartgame());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(gameUtil, this);
        pm.registerEvents(new ResourceListener(), this);
        pm.registerEvents(new PlayerJoinAndLeave(), this);
        pm.registerEvents(new GameListener(), this);
    }
}