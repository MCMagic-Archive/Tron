package us.mcmagic.tron.clock.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.mcmagic.mcmagiccore.MCMagicCore;
import us.mcmagic.mcmagiccore.audioserver.PacketHelper;
import us.mcmagic.mcmagiccore.audioserver.packets.PacketPlayOnceGlobal;

/**
 * Created by Marc on 1/26/16
 */
public class MusicAction extends GameAction {
    private final String fileName;

    public MusicAction(long time, String fileName) {
        super(time);
        this.fileName = fileName;
    }

    @Override
    public void playAction() {
        PacketPlayOnceGlobal packet = new PacketPlayOnceGlobal(MCMagicCore.audioServer.getAudioid(), "tron/" + fileName, 1);
        for (Player tp : Bukkit.getOnlinePlayers()) {
            PacketHelper.sendToPlayer(packet, tp);
        }
    }
}