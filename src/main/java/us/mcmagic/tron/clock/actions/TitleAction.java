package us.mcmagic.tron.clock.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.mcmagic.mcmagiccore.title.TitleObject;

/**
 * Created by Marc on 2/7/16
 */
public class TitleAction extends GameAction {
    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public TitleAction(long time, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        super(time);
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    @Override
    public void playAction() {
        TitleObject title = new TitleObject(this.title, subtitle).setFadeIn(fadeIn).setStay(stay).setFadeOut(fadeOut);
        for (Player tp : Bukkit.getOnlinePlayers()) {
            title.send(tp);
        }
    }
}