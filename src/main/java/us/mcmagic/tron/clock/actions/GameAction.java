package us.mcmagic.tron.clock.actions;

/**
 * Created by Marc on 12/26/15
 */
public abstract class GameAction {
    private long time;

    public GameAction(long time) {
        this.time = time;
    }

    public abstract void playAction();

    public long getTime() {
        return time;
    }
}