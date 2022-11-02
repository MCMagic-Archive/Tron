package us.mcmagic.tron.game.lasertag;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by Marc on 12/27/15
 */
public class Laser {
    private ArmorStand stand;
    private UUID shooter;
    private Vector vector;
    private LaserTeam team;

    public Laser(ArmorStand stand, UUID shooter, Vector vector, LaserTeam team) {
        this.stand = stand;
        this.shooter = shooter;
        this.vector = vector;
        this.team = team;
    }

    public ArmorStand getStand() {
        return stand;
    }

    public Location getLocation() {
        return stand.getLocation();
    }

    public UUID getShooter() {
        return shooter;
    }

    public Vector getVector() {
        return vector;
    }

    public LaserTeam getTeam() {
        return team;
    }
}