package us.mcmagic.tron.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import us.mcmagic.tron.Tron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marc on 2/6/16
 */
public class LobbyCountdownUtil {

    public static void countdownNumber(int num) {
        clearSpace();
        final List<Location> list = getEnum(num);
        for (Location loc : list) {
            loc.getBlock().setType(Material.GLOWSTONE);
        }
        Bukkit.getScheduler().runTaskLater(Tron.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Location loc : list) {
                    loc.getBlock().setType(Material.SEA_LANTERN);
                }
            }
        }, 10L);
    }

    public static void clearSpace() {
        World world = Bukkit.getWorlds().get(0);
        for (int x = 0; x <= 2; x++) {
            for (int y = 66; y <= 70; y++) {
                Block b = world.getBlockAt(new Location(world, x, y, -7));
                b.setType(Material.AIR);
            }
        }
    }

    private static List<Location> getEnum(int num) {
        switch (num) {
            case 5:
                return CountdownNumber.FIVE.getLocations();
            case 4:
                return CountdownNumber.FOUR.getLocations();
            case 3:
                return CountdownNumber.THREE.getLocations();
            case 2:
                return CountdownNumber.TWO.getLocations();
            case 1:
                return CountdownNumber.ONE.getLocations();
        }
        return new ArrayList<>();
    }

    private enum CountdownNumber {
        FIVE, FOUR, THREE, TWO, ONE;

        public List<Location> getLocations() {
            switch (this) {
                case FIVE:
                    return Arrays.asList(getLoc(0, 66, -7), getLoc(1, 66, -7), getLoc(2, 66, -7), getLoc(2, 67, -7),
                            getLoc(0, 68, -7), getLoc(1, 68, -7), getLoc(2, 68, -7), getLoc(0, 69, -7),
                            getLoc(0, 70, -7), getLoc(1, 70, -7), getLoc(2, 70, -7));
                case FOUR:
                    return Arrays.asList(getLoc(2, 66, -7), getLoc(2, 67, -7), getLoc(0, 68, -7), getLoc(1, 68, -7),
                            getLoc(2, 68, -7), getLoc(0, 69, -7), getLoc(0, 70, -7), getLoc(2, 70, -7),
                            getLoc(2, 69, -7));
                case THREE:
                    return Arrays.asList(getLoc(0, 66, -7), getLoc(1, 66, -7), getLoc(2, 66, -7), getLoc(2, 67, -7),
                            getLoc(1, 68, -7), getLoc(2, 68, -7), getLoc(0, 70, -7), getLoc(2, 70, -7),
                            getLoc(1, 70, -7), getLoc(2, 69, -7));
                case TWO:
                    return Arrays.asList(getLoc(0, 66, -7), getLoc(1, 66, -7), getLoc(2, 66, -7), getLoc(0, 68, -7),
                            getLoc(0, 67, -7), getLoc(1, 68, -7), getLoc(2, 68, -7), getLoc(0, 70, -7),
                            getLoc(2, 70, -7), getLoc(1, 70, -7), getLoc(2, 69, -7));
                case ONE:
                    return Arrays.asList(getLoc(0, 66, -7), getLoc(1, 66, -7), getLoc(2, 66, -7), getLoc(1, 67, -7),
                            getLoc(1, 68, -7), getLoc(0, 70, -7), getLoc(1, 70, -7), getLoc(1, 69, -7));
            }
            return new ArrayList<>();
        }

        private Location getLoc(int x, int y, int z) {
            return new Location(Bukkit.getWorlds().get(0), x, y, z);
        }
    }
}