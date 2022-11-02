package us.mcmagic.tron.clock.actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import us.mcmagic.mcmagiccore.itemcreator.ItemCreator;
import us.mcmagic.tron.Tron;
import us.mcmagic.tron.game.lasertag.LaserTeam;

import java.util.UUID;

/**
 * Created by Marc on 12/27/15
 */
public class LaserRespawn extends GameAction {
    private UUID uuid;
    private static final ItemStack blueLaser = new ItemCreator(Material.DIAMOND_AXE, ChatColor.AQUA + "Blue Laser Gun");
    private static final ItemStack redLaser = new ItemCreator(Material.GOLD_AXE, ChatColor.RED + "Red Laser Gun");

    public LaserRespawn(long time, UUID uuid) {
        super(time);
        this.uuid = uuid;
    }

    @Override
    public void playAction() {
        Player player = Bukkit.getPlayer(uuid);
        LaserTeam team = Tron.gameUtil.getLaserTag().getTeam(player);
        player.teleport(Tron.gameUtil.getLaserTag().getSpawn(team));
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setAllowFlight(false);
        PlayerInventory inv = player.getInventory();
        switch (team) {
            case BLUE:
                inv.setItem(0, blueLaser);
                break;
            case RED:
                inv.setItem(0, redLaser);
                break;
        }
        for (Player tp : Bukkit.getOnlinePlayers()) {
            if (tp.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }
            tp.showPlayer(player);
        }
        for (UUID uuid : Tron.gameUtil.getLaserTag().getRespawning()) {
            if (uuid.equals(player.getUniqueId())) {
                continue;
            }
            player.hidePlayer(Bukkit.getPlayer(uuid));
        }
        player.teleport(Tron.gameUtil.getLaserTag().getTeamSpawn(team));
        Tron.gameUtil.getLaserTag().respawned(player.getUniqueId());
    }
}
