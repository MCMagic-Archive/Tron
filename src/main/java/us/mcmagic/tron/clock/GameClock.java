package us.mcmagic.tron.clock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.Vector;
import us.mcmagic.tron.Tron;
import us.mcmagic.tron.clock.actions.ChangeGame;
import us.mcmagic.tron.clock.actions.GameAction;
import us.mcmagic.tron.game.LaserTag;
import us.mcmagic.tron.game.lasertag.Laser;
import us.mcmagic.tron.handlers.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marc on 12/26/15
 */
public class GameClock implements Runnable {
    private List<GameAction> actions = new ArrayList<>();
    private static List<Laser> lasers = new ArrayList<>();
    private static List<BlockFace> faces = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH,
            BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
    private long gameTime = 0;

    @Override
    public void run() {
        if (gameTime == 1) {
            switch (GameState.getState()) {
                case LASERTAG:
                    actions.add(new ChangeGame(100, Tron.gameUtil.getDiscWars()));
                    break;
                case DISCWARS:
                    actions.add(new ChangeGame(100, Tron.gameUtil.getDiscWars()));
                    break;
                case LIGHTCYCLES:
                    break;
            }
            Tron.gameUtil.getCurrentGame().end();
            GameState.setState(GameState.BETWEEN);
            gameTime = 0;
        }
        if (GameState.isState(GameState.LASERTAG)) {
            for (Player tp : Bukkit.getOnlinePlayers()) {
                Objective obj = tp.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
                obj.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Laser Tag " + ChatColor.RED +
                        formatTime(gameTime));
            }
            LaserTag lt = Tron.gameUtil.getLaserTag();
            lt.recharge();
            for (Laser laser : new ArrayList<>(lasers)) {
                Location to = laser.getLocation().add(laser.getVector());
                Location calc = to.clone().add(0, 1.5, 0);
                if (nearBlock(calc)) {
                    //if (!calc.getBlock().getType().equals(Material.AIR)) {
                    laser.getStand().remove();
                    lasers.remove(laser);
                    continue;
                }
                boolean removed = false;
                for (Player tp : lt.getTeam(laser.getTeam().opposite())) {
                    if (lt.isRespawning(tp.getUniqueId())) {
                        continue;
                    }
                    double dist = getDistance(tp.getLocation().add(0, 1.5, 0), calc);
                    if (dist == -1) {
                        continue;
                    }
                    if (dist <= 1) {
                        lt.hit(tp, Bukkit.getPlayer(laser.getShooter()));
                        laser.getStand().remove();
                        lasers.remove(laser);
                        removed = true;
                        break;
                    }
                }
                if (removed) {
                    continue;
                }
                Vector vector = laser.getVector();
                laser.getStand().teleport(to);
            }
        }
        for (GameAction action : new ArrayList<>(actions)) {
            if (System.currentTimeMillis() < action.getTime()) {
                continue;
            }
            action.playAction();
            actions.remove(action);
        }
        if (gameTime > 1) {
            gameTime -= 1;
        }

    }

    private double getDistance(Location loc, Location loc2) {
        if (loc.getY() - loc2.getY() > 1) {
            return -1;
        }
        return Math.sqrt(Math.pow((loc.getX() - loc2.getX()), 2) + Math.pow((loc.getZ() - loc2.getZ()), 2));
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    private boolean nearBlock(Location loc) {
        double x = loc.getX() % 1;
        double y = loc.getY() % 1;
        double z = loc.getZ() % 1;
        if (x < 0) {
            x = 1 - (x * -1);
        }
        if (y < 0) {
            return true;
        }
        if (z < 0) {
            z = 1 - (z * -1);
        }
        List<BlockFace> faces = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
        Block b = loc.getBlock();
        Block n = b.getRelative(BlockFace.NORTH);
        if (isNotAir(n) && z < .1) {
            return true;
        }
        Block e = b.getRelative(BlockFace.EAST);
        if (isNotAir(e) && x > .9) {
            return true;
        }
        Block s = b.getRelative(BlockFace.SOUTH);
        if (isNotAir(s) && z > .9) {
            return true;
        }
        Block w = b.getRelative(BlockFace.WEST);
        if (isNotAir(w) && x < .1) {
            return true;
        }
        Block u = b.getRelative(BlockFace.UP);
        if (isNotAir(u) && y > .9) {
            return true;
        }
        Block d = b.getRelative(BlockFace.DOWN);
        return isNotAir(d) && y < .1;
    }

    private boolean isNotAir(Block b) {
        return !b.getType().equals(Material.AIR);
    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    public void trackLaser(Laser laser) {
        lasers.add(laser);
    }

    private static String formatTime(long time) {
        int minutes = (int) ((time / 20) / 60);
        int seconds = (int) ((time / 20) % 60);
        return String.format("%d:%02d", minutes, seconds);
    }
}