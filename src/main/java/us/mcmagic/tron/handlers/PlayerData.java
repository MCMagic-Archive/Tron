package us.mcmagic.tron.handlers;

import java.util.UUID;

/**
 * Created by Marc on 12/29/15
 */
public class PlayerData {
    private UUID uuid;
    private int laserTagHits;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.laserTagHits = 0;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public int getLaserTagHits() {
        return laserTagHits;
    }

    public void setLaserTagHits(int laserTagHits) {
        this.laserTagHits = laserTagHits;
    }
}