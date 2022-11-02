package us.mcmagic.tron.game;

import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import us.mcmagic.mcmagiccore.MCMagicCore;
import us.mcmagic.mcmagiccore.actionbar.ActionBarManager;
import us.mcmagic.mcmagiccore.itemcreator.ItemCreator;
import us.mcmagic.tron.Tron;
import us.mcmagic.tron.clock.actions.LaserRespawn;
import us.mcmagic.tron.game.lasertag.Laser;
import us.mcmagic.tron.game.lasertag.LaserTeam;
import us.mcmagic.tron.handlers.GameState;
import us.mcmagic.tron.handlers.PlayerData;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Marc on 12/26/15
 */
public class LaserTag extends TronGame {
    private List<UUID> blue = new ArrayList<>();
    private List<UUID> red = new ArrayList<>();
    private int blueScore = 0;
    private int redScore = 0;
    private List<UUID> respawning = new ArrayList<>();
    private ItemStack blueArmor = new ItemCreator(Material.CARPET, ChatColor.AQUA + "Blue Armor");
    private ItemStack blueLaser = new ItemCreator(Material.DIAMOND_AXE, ChatColor.AQUA + "Blue Laser Gun");
    private ItemStack redArmor = new ItemCreator(Material.CARPET, 1, (byte) 1, ChatColor.RED + "Red Armor",
            new ArrayList<String>());
    private ItemStack redLaser = new ItemCreator(Material.GOLD_AXE, ChatColor.RED + "Red Laser Gun");
    private Location blueLobby = new Location(Bukkit.getWorlds().get(0), -35.5, 59, 7.5, 0, 0);
    private Location redLobby = new Location(Bukkit.getWorlds().get(0), -35.5, 59, -37.5, 180, 0);
    private HashMap<UUID, Double> recharging = new HashMap<>();

    @Override
    public void start() {
        GameState.setState(GameState.LASERTAG);
        Tron.gameUtil.loadActions(GameState.LASERTAG);
        scoreboard();
        divideIntoTeams();
        objective();
        for (Player tp : Bukkit.getOnlinePlayers()) {
            inventory(tp);
        }
        Tron.gameUtil.clock.setGameTime(4800);
    }

    @Override
    public void end() {
        for (Player tp : Bukkit.getOnlinePlayers()) {
            tp.getInventory().clear();
            Scoreboard sb = tp.getScoreboard();
            sb.getObjective(DisplaySlot.SIDEBAR).unregister();
        }
    }

    @Override
    public void handle(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();
        LaserTeam team = getTeam(player);
        event.setCancelled(true);
        MCMagicCore.chatManager.chatMessage(player, msg, team.getNameWithBrackets() + " ");
    }

    public void inventory(Player tp) {
        PlayerInventory inv = tp.getInventory();
        inv.clear();
        switch (getTeam(tp)) {
            case BLUE:
                inv.setHelmet(blueArmor);
                inv.setItem(0, blueLaser);
                break;
            case RED:
                inv.setHelmet(redArmor);
                inv.setItem(0, redLaser);
                break;
        }
        inv.setHeldItemSlot(0);
    }

    private void scoreboard() {
        ScoreboardManager sbm = Bukkit.getScoreboardManager();
        for (Player tp : Bukkit.getOnlinePlayers()) {
            Scoreboard sb = sbm.getNewScoreboard();
            Team blue = sb.registerNewTeam("blue");
            blue.setPrefix(ChatColor.AQUA + "[BLUE] ");
            blue.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
            Team red = sb.registerNewTeam("red");
            red.setPrefix(ChatColor.RED + "[RED] ");
            red.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
            tp.setScoreboard(sb);
        }
    }

    private void objective() {
        for (Player tp : Bukkit.getOnlinePlayers()) {
            Scoreboard sb = tp.getScoreboard();
            Objective obj = sb.registerNewObjective("main", "dummy");
            obj.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Laser Tag " + ChatColor.RED + "4:00");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.getScore("    ").setScore(10);
            obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Blue Derezzes").setScore(9);
            obj.getScore(ChatColor.AQUA + "" + 0).setScore(8);
            obj.getScore("   ").setScore(7);
            obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Red Derezzes").setScore(6);
            obj.getScore(ChatColor.RED + "" + 0).setScore(5);
            obj.getScore("  ").setScore(4);
            obj.getScore(ChatColor.YELLOW + "Your Derezzes").setScore(3);
            obj.getScore(ChatColor.YELLOW + "" + 0).setScore(2);
            obj.getScore(" ").setScore(1);
        }
    }

    public LaserTeam getTeam(Player tp) {
        if (blue.contains(tp.getUniqueId())) {
            return LaserTeam.BLUE;
        }
        if (red.contains(tp.getUniqueId())) {
            return LaserTeam.RED;
        }
        return LaserTeam.BLUE;
    }

    public List<Player> getTeam(LaserTeam team) {
        List<Player> list = new ArrayList<>();
        switch (team) {
            case BLUE:
                for (UUID uuid : new ArrayList<>(blue)) {
                    list.add(Bukkit.getPlayer(uuid));
                }
                break;
            case RED:
                for (UUID uuid : new ArrayList<>(red)) {
                    list.add(Bukkit.getPlayer(uuid));
                }
                break;
        }
        return list;
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material type = player.getItemInHand().getType();
        if (type.equals(Material.DIAMOND_AXE) || type.equals(Material.GOLD_AXE)) {
            shootLaser(player);
        }
    }

    private void shootLaser(Player player) {
        if (recharging.containsKey(player.getUniqueId())) {
            return;
        }
        recharging.put(player.getUniqueId(), 20.0);
        LaserTeam team = getTeam(player);
        Location loc = player.getLocation().add(0, -0.25, 0);
        PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect("tron/lasertag/shoot",
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1.0f, 1.0f);
        for (Player tp : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) tp).getHandle().playerConnection.sendPacket(packet);
        }
        float pitch = player.getLocation().getPitch();
        Vector vector = loc.getDirection().multiply(1.75);
        ArmorStand stand = player.getWorld().spawn(loc, ArmorStand.class);
        stand.setHeadPose(new EulerAngle(pitch >= 0 ? pitch : 360 + pitch, 0, 0));
        stand.setVisible(false);
        stand.setHelmet(new ItemCreator(team.getLaser()));
        stand.setGravity(false);
        Laser laser = new Laser(stand, player.getUniqueId(), vector, team);
        Tron.gameUtil.clock.trackLaser(laser);
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
        Player player = event.getPlayer();
        Material type = player.getItemInHand().getType();
        if (type.equals(Material.DIAMOND_AXE) || type.equals(Material.GOLD_AXE)) {
            shootLaser(player);
        }
    }

    private void divideIntoTeams() {
        List<Player> players = new ArrayList<>();
        for (Player tp : Bukkit.getOnlinePlayers()) {
            players.add(tp);
        }
        Collections.shuffle(players, new Random(System.nanoTime()));
        LaserTeam currentTeam = LaserTeam.BLUE;
        for (Player tp : players) {
            addToTeam(currentTeam, tp.getUniqueId());
            MCMagicCore.gameManager.message(tp, "You have joined the " + currentTeam.getName() + " Team!");
            setScoreboardTeam(tp, currentTeam);
            switch (currentTeam) {
                case BLUE:
                    tp.teleport(blueLobby);
                    break;
                case RED:
                    tp.teleport(redLobby);
                    break;
            }
            currentTeam = currentTeam.opposite();
        }
    }

    private void setScoreboardTeam(Player player, LaserTeam team) {
        for (Player tp : Bukkit.getOnlinePlayers()) {
            Scoreboard sb = tp.getScoreboard();
            sb.getTeam(team.name().toLowerCase()).addEntry(player.getName());
        }
    }

    public void addToTeam(LaserTeam team, UUID uuid) {
        switch (team) {
            case BLUE:
                blue.add(uuid);
                break;
            case RED:
                red.add(uuid);
                break;
        }
    }

    public void hit(Player target, Player shooter) {
        LaserTeam team = getTeam(shooter);
        MCMagicCore.gameManager.broadcast(team.getColor() + shooter.getName() + ChatColor.GREEN +
                " derezzed " + getTeam(target).getColor() + target.getName());
        addPoint(team, shooter);
        target.getInventory().setItem(0, new ItemStack(Material.AIR));
        target.setAllowFlight(true);
        target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200000, 0));
        respawning.add(target.getUniqueId());
        for (Player tp : Bukkit.getOnlinePlayers()) {
            if (tp.getUniqueId().equals(target.getUniqueId()) || isRespawning(tp.getUniqueId())) {
                continue;
            }
            tp.hidePlayer(target);
        }
        Tron.gameUtil.clock.addAction(new LaserRespawn(System.currentTimeMillis() + 5000, target.getUniqueId()));
    }

    public boolean isRespawning(UUID uuid) {
        return respawning.contains(uuid);
    }

    private void addPoint(LaserTeam team, Player shooter) {
        final int score = team == LaserTeam.BLUE ? blueScore : redScore;
        int place = team == LaserTeam.BLUE ? 8 : 5;
        for (Player tp : Bukkit.getOnlinePlayers()) {
            Scoreboard sb = tp.getScoreboard();
            Objective obj = sb.getObjective(DisplaySlot.SIDEBAR);
            sb.resetScores(team.getColor() + "" + score);
            obj.getScore(team.getColor() + "" + (score + 1)).setScore(place);
        }
        switch (team) {
            case BLUE:
                blueScore++;
                break;
            case RED:
                redScore++;
                break;
        }
        PlayerData data = Tron.getPlayerData(shooter.getUniqueId());
        Scoreboard sb = shooter.getScoreboard();
        Objective obj = sb.getObjective(DisplaySlot.SIDEBAR);
        sb.resetScores(ChatColor.YELLOW + "" + data.getLaserTagHits());
        obj.getScore(ChatColor.YELLOW + "" + (data.getLaserTagHits() + 1)).setScore(2);
        data.setLaserTagHits(data.getLaserTagHits() + 1);
    }

    public Location getSpawn(LaserTeam team) {
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    public void respawned(UUID uuid) {
        respawning.remove(uuid);
    }

    public List<UUID> getRespawning() {
        return new ArrayList<>(respawning);
    }

    public Location getTeamSpawn(LaserTeam team) {
        switch (team) {
            case BLUE:
                return blueLobby;
            case RED:
                return redLobby;
        }
        return null;
    }

    public void recharge() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        for (Map.Entry<UUID, Double> entry : new HashSet<>(recharging.entrySet())) {
            UUID uuid = entry.getKey();
            Player tp = Bukkit.getPlayer(uuid);
            if (tp == null) {
                recharging.remove(uuid);
                tp.setExp(0.0F);
                continue;
            }
            double level = entry.getValue();
            if (level == 20) {
                ActionBarManager.sendMessage(tp, ChatColor.AQUA + "" + ChatColor.BOLD + "Recharging");
            }
            if (level <= 0) {
                recharging.remove(uuid);
                ActionBarManager.sendMessage(tp, ChatColor.AQUA + "" + ChatColor.BOLD + "Recharge Completed!");
                tp.setExp(1.0F);
                continue;
            }
            tp.setExp((float) (0.05 * (20 - level)));
            if (level % 6 == 0) {
                if (level <= 6) {
                    ActionBarManager.sendMessage(tp, ChatColor.AQUA + "" + ChatColor.BOLD + "Recharging...");
                } else if (level <= 12) {
                    ActionBarManager.sendMessage(tp, ChatColor.AQUA + "" + ChatColor.BOLD + "Recharging..");
                } else if (level <= 18) {
                    ActionBarManager.sendMessage(tp, ChatColor.AQUA + "" + ChatColor.BOLD + "Recharging.");

                }
            }
            recharging.put(uuid, level - 1);
        }
    }
}