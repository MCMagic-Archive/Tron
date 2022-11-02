package us.mcmagic.tron.clock.actions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Marc on 12/26/15
 */
public class TeleportAll extends GameAction {
    private Location loc;

    public TeleportAll(long time, Location loc) {
        super(time);
        this.loc = loc;
    }

    @Override
    public void playAction() {
        for (Player tp : Bukkit.getOnlinePlayers()) {
            tp.teleport(loc);
        }
    }
}